package com.hzcloud.device.config;

import com.hzcloud.card.domain.AcsCard;
import com.hzcloud.card.service.IAcsCardService;
import com.hzcloud.device.AcsDeviceController;
import com.hzcloud.device.AcsDeviceControllerFactory;
import com.hzcloud.device.bo.AcsDeviceResult;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.domain.AcsDeviceDoor;
// import com.hzcloud.device.hikvision.K1T604TM;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.device.service.IAcsDeviceDoorService;
import com.hzcloud.device.syris.V6Alarm;
import com.hzcloud.info.domain.AcsAlarmInfo;
import com.hzcloud.info.service.IAcsAlarmInfoService;
import com.hzcloud.v6.domain.AcsV6CardIndex;
import com.hzcloud.v6.service.IAcsV6CardIndexService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class DeviceConfig {

    @Autowired
    private IAcsDeviceConService deviceConService;

    @Autowired
    private IAcsDeviceDoorService deviceDoorService;

    @Autowired
    private IAcsAlarmInfoService alarmInfoService;

    @Autowired
    private IAcsV6CardIndexService cardIndexService;

    @Autowired
    private IAcsCardService cardService;

    // @Autowired
    // private FMSGCallBack_V31 fmsgCallBack_v31;

    /**
     * 初始化方法
     * 查询控制器列表并初始化并登录
     */
    @PostConstruct
    public void init() {
        // K1T604TM.hCNetSDK.NET_DVR_Init();
//        List<AcsDeviceCon> deviceConList = deviceConService.selectAcsDeviceConList(null);
        // K1T604TM.setCallBack(fmsgCallBack_v31);
        
//        for (AcsDeviceCon deviceCon : deviceConList) {
//            if(!DeviceConStatus.getMap().containsKey(deviceCon.getIp())){
//                AcsDeviceController deviceController = AcsDeviceControllerFactory.CreateController(deviceCon.getType());
//                DeviceConStatus.addDeviceController(deviceCon.getIp(), deviceController);
//                AcsDeviceResult loginStatus = deviceController.Login(deviceCon.getIp(),
//                        deviceCon.getUserName(),
//                        deviceCon.getPassword(),
//                        Integer.parseInt(String.valueOf(deviceCon.getPort())),
//                        deviceCon.getIdentifier());
//                log.info("控制器{}登录{}",
//                        deviceCon.getIp(),
//                        loginStatus.isCode() ? "成功" : "失败"
//                        );
//                if (deviceCon.getAlarmStatus().equals("1")){
//                    deviceController.SetupAlarmChan();
//                }
//            }
//        }
        
        for(Map.Entry<String, AcsDeviceController> entry : DeviceConStatus.getMap().entrySet()){
            String ip = entry.getKey();
            AcsDeviceController controller = entry.getValue();
            new Thread(new MonitorThread(controller,ip)).start();
        }
    }

    public void StartNewMonitorThread(AcsDeviceController controller, String ip){
        new Thread(new MonitorThread(controller,ip)).start();
    }

    /**
     * 监控线程
     */
    private class MonitorThread implements Runnable {
        private volatile AcsDeviceController controller;
        private volatile String ip;

        public MonitorThread(AcsDeviceController controller, String ip){
            this.controller = controller;
            this.ip = ip;
        }

        @Override
        public void run() {
            log.info("启动监测线程:"+ this.ip);
            while (true) {
                try{
                    List<AcsDeviceCon> conlist =  deviceConService.selectConByIp(ip);
                    for(AcsDeviceCon con:conlist){
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            log.error("异常信息:{}", e.getMessage());
                        }
                        log.info("开始检查控制器"+con.getControllerName());
                        AcsDeviceResult result = controller.CheckConnect(con.getControllerIndex());
                        if(!result.isCode()){
                            log.info("重新连接"+con.getControllerName());
                            result = controller.Login(con.getIp(),
                                con.getUserName(),
                                con.getPassword(),
                                Integer.parseInt(String.valueOf(con.getPort())),
                                con.getIdentifier()
                                );
                        }
                        if(result.isCode()){
                            AcsDeviceCon tempcon = new AcsDeviceCon();
                            tempcon.setControllerId(con.getControllerId());
                            tempcon.setStatus("1");
                            deviceConService.updateConStatus(tempcon);
                            //处理门状态
                            AcsDeviceDoor doorsearch = new AcsDeviceDoor();
                            doorsearch.setControllerId(con.getControllerId());
                            List<AcsDeviceDoor> doorlist = deviceDoorService.selectAcsDeviceDoorList(doorsearch);
                            result = controller.getDoorStatus(con.getControllerIndex());
                            if(!result.isCode()){
                                continue;
                            }
                            String resp = result.getMessage();
                            char door1Status = resp.charAt(0);
                            char door2Status = resp.charAt(1);
                            char door3Status = resp.charAt(2);
                            char door4Status = resp.charAt(3);
                            Optional<AcsDeviceDoor> doorOptional = doorlist.stream().filter(item -> item.getPin().equals(1)).findFirst();
                            if(doorOptional.isPresent()){
                                String oldstatus = doorOptional.get().getStatus();
                                if(!((oldstatus.equals("1") && door1Status =='1') || (oldstatus.equals("2") && door1Status == '0'))){
                                    doorOptional.get().setStatus((door1Status == '0')?"0":"4");
                                    deviceDoorService.updateAcsDeviceDoor(doorOptional.get());
                                }
                            }
                            doorOptional = doorlist.stream().filter(item -> item.getPin().equals(2)).findFirst();
                            if(doorOptional.isPresent()){
                                String oldstatus = doorOptional.get().getStatus();
                                if(!((oldstatus.equals("1") && door2Status =='1') ||(oldstatus.equals("2") && door2Status =='0'))){
                                    doorOptional.get().setStatus((door2Status == '0')?"0":"4");
                                    deviceDoorService.updateAcsDeviceDoor(doorOptional.get());
                                }
                            }
                            doorOptional = doorlist.stream().filter(item -> item.getPin().equals(3)).findFirst();
                            if(doorOptional.isPresent()){
                                String oldstatus = doorOptional.get().getStatus();
                                if(!((oldstatus.equals("1") && door3Status =='1') ||(oldstatus.equals("2") && door3Status =='0'))){
                                    doorOptional.get().setStatus((door3Status == '0')?"0":"4");
                                    deviceDoorService.updateAcsDeviceDoor(doorOptional.get());
                                }
                            }
                            doorOptional = doorlist.stream().filter(item -> item.getPin().equals(4)).findFirst();
                            if(doorOptional.isPresent()){
                                String oldstatus = doorOptional.get().getStatus();
                                if(!((oldstatus.equals("1") && door4Status =='1') ||(oldstatus.equals("2") && door4Status =='0'))){
                                    doorOptional.get().setStatus((door4Status == '0')?"0":"4");
                                    deviceDoorService.updateAcsDeviceDoor(doorOptional.get());
                                }
                            }
                            result = controller.getRecordAndTotal(con.getControllerIndex());
                            if(!result.isCode()){
                                continue;
                            }
                            resp = result.getMessage();
                            String num = resp.substring(0, 4);
                            while(!num.equals("0000")){
                                String id = resp.substring(4, 5);
                                int pin = Integer.parseInt(id);
                                if (pin>4){
                                    pin -= 4;
                                }
                                String time = resp.substring(5, 19);
                                String cardIndexstr = resp.substring(19, 23);
                                String flag = resp.substring(23,28);
                                V6Alarm v6Alarm = new V6Alarm(flag);
                                if(v6Alarm.getAlarmType() != null){
                                    AcsAlarmInfo alarmInfo = new AcsAlarmInfo();
                                    try {
                                        alarmInfo.setAlarmTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(time));
                                    } catch (ParseException e) {
                                        log.error("异常信息:{}", e.getMessage());
                                    }
                                    alarmInfo.setAlarmDetail(v6Alarm.getAlarmDetail());
                                    alarmInfo.setAlarmType(v6Alarm.getAlarmType());
                                    alarmInfo.setControllerId(con.getControllerId());
                                    alarmInfo.setControllerIp(con.getIp());
                                    alarmInfo.setControllerName(con.getControllerName());
                                    if(pin != 0){
                                        alarmInfo.setDoorPin((long)pin);
                                        int pinsearch = pin;
                                        doorOptional = doorlist.stream().filter(item -> item.getPin().equals(pinsearch)).findFirst();
                                        if(doorOptional.isPresent()){
                                            alarmInfo.setDoorName(doorOptional.get().getDoorName());
                                        }
                                    }
                                    //处理卡号相关
                                    if(cardIndexstr.charAt(0) != 'X'){
                                        //有效卡
                                        AcsV6CardIndex cardIndex = new AcsV6CardIndex();
                                        cardIndex.setControllerId(con.getControllerId());
                                        cardIndex.setCardIndex(cardIndexstr);
                                        List<AcsV6CardIndex> list =cardIndexService.selectAcsV6CardIndexList(cardIndex);
                                        if(list.size() > 0){
                                            AcsCard card = cardService.selectAcsCardById(list.get(0).getCardId());
                                            if(card != null){
                                                alarmInfo.setCardNumber(card.getCardNumber());
                                                alarmInfo.setUserName(card.getUserName());
                                            }
                                        }
                                    }else if(cardIndexstr.charAt(2) != 'X'){
                                        //获取未知卡号
                                        result = controller.getCardNumNoInSystem(con.getControllerIndex(), cardIndexstr.substring(2, 4));
                                        if(result.isCode()){
                                            alarmInfo.setCardNumber(result.getMessage());
                                        }
                                    }
                                    //记录
                                    alarmInfoService.insertAcsAlarmInfo(alarmInfo);
                                }
                                result= controller.getRecordAndTotal(con.getControllerIndex());
                                if(!result.isCode()){
                                    break;
                                }
                                resp = result.getMessage();
                                num = resp.substring(0,4);
                            }
                        }
                        else{
                            log.info("控制器离线"+con.getControllerName());
                            con.setStatus("0");
                            deviceConService.updateConStatus(con);
                            AcsDeviceDoor doorsearch = new AcsDeviceDoor();
                            doorsearch.setControllerId(con.getControllerId());
                            List<AcsDeviceDoor> doorlist = deviceDoorService.selectAcsDeviceDoorList(doorsearch);
                            for(AcsDeviceDoor door:doorlist){
                                door.setStatus("3");
                                deviceDoorService.updateDoorStatus(door);
                            }
                        }         
                    }
                }catch(Exception e) {
                    log.error("系统异常:",e);
                }
            }
            
        }   
    }
}
