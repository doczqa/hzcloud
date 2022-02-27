package com.hzcloud.device.syris;

import java.io.IOException;

import com.hzcloud.device.AcsDeviceController;
import com.hzcloud.device.bo.AcsDeviceAppGroupSettingInfo;
import com.hzcloud.device.bo.AcsDeviceCardInfo;
import com.hzcloud.device.bo.AcsDeviceCommandInfo;
import com.hzcloud.device.bo.AcsDeviceFaceInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayGroupInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayInfo;
import com.hzcloud.device.bo.AcsDeviceHolidayPlanInfo;
import com.hzcloud.device.bo.AcsDeviceResult;
import com.hzcloud.device.bo.AcsDeviceTimeIntervalInfo;
import com.hzcloud.device.bo.AcsDeviceTimeZoneInfo;
import com.hzcloud.device.bo.AcsDeviceUserInfo;
import com.hzcloud.device.bo.AcsDeviceWeekTemplateInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class V6Controller implements AcsDeviceController{
    
    private V6 v6= new V6();

    @Override
    public AcsDeviceResult Login(String ip, String username, String pw, int port, String identifier) {
        boolean ret = this.v6.ConnectSocket(ip, port);
        if(ret){
            return new AcsDeviceResult(ret,"控制器登陆成功");
        }
        return new AcsDeviceResult(false, "控制器登陆失败");
    }

    @Override
    public void Logout() {   
        try {
            this.v6.getV6Socket().close();
            this.v6.setV6Socket(null);
        } catch (IOException e) {
            log.error("系统异常:",e);
            e.printStackTrace();
        }     
    }

    @Override
    public AcsDeviceResult SetUser(AcsDeviceUserInfo userInfo) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "设置用户成功");
    }

    @Override
    public AcsDeviceResult SetCard(AcsDeviceCardInfo cardInfo) {
        AcsDeviceResult result = new AcsDeviceResult(false,"设置卡片失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        log.info("下发卡片");
        result = this.v6.getOneCard(cardInfo.getControllerIndex(),cardInfo.getCardIndex());
        if(!result.isCode()){
            return result;
        }
        log.info("获取卡片原数据");
        if(result.getMessage() == ""){
            int doorPin = cardInfo.getDoorRight();
            if (doorPin > 4){
                doorPin -=4;
            }
            String app = String.format("%02d", 1<<doorPin);
            result = this.v6.insertOneCard(cardInfo.getControllerIndex(), cardInfo.getCardIndex(), cardInfo.getCardNumber(), app , "0", "XXXX", cardInfo.getAPB());
            log.info("添加一张卡片");
        }else{
            //修改卡
            int doorPin = cardInfo.getDoorRight();
            if (doorPin > 4){
                doorPin -=4;
            }
            String app = result.getMessage().substring(16, 18);
            int appInt =Integer.valueOf(app).intValue();
            appInt = appInt | (1<<(doorPin - 1));
            app = String.format("%02d", appInt);
            result = this.v6.insertOneCard(cardInfo.getControllerIndex(), cardInfo.getCardIndex(), cardInfo.getCardNumber(), app , "0", "XXXX", cardInfo.getAPB());
            log.info("修改一张卡片");
        }
        return result;
    }

    @Override
    public AcsDeviceResult SetFace(AcsDeviceFaceInfo faceInfo) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "设置人脸成功");
    }

    @Override
    public AcsDeviceResult DelUser(AcsDeviceUserInfo userInfo) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "删除用户成功");
    }

    @Override
    public AcsDeviceResult DelCard(AcsDeviceCardInfo cardInfo) {
        AcsDeviceResult result = new AcsDeviceResult(false,"删除卡片失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        result = this.v6.getOneCard(cardInfo.getControllerIndex(),cardInfo.getCardIndex());
        if(!result.isCode()){
            return result;
        }
        if(result.getMessage() == ""){
            result.setCode(true);
            result.setMessage("无卡片记录");
        }else{
            //修改卡
            int doorPin = cardInfo.getDoorRight();
            if (doorPin > 4){
                doorPin -=4;
            }
            String app = result.getMessage().substring(16, 18);
            int appInt =Integer.valueOf(app).intValue();
            appInt = appInt & ~(1<<(doorPin - 1));
            app = String.format("%02d", appInt);
            result = this.v6.insertOneCard(cardInfo.getControllerIndex(), cardInfo.getCardIndex(), cardInfo.getCardNumber(), app , "0", "XXXX", cardInfo.getAPB());
            if(result.isCode()){
                result.setMessage("删除卡片成功");
            }
        }
        return result;
    }

    @Override
    public AcsDeviceResult EnableUser(AcsDeviceUserInfo userInfo) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "使能用户成功");
    }

    @Override
    public AcsDeviceResult EnableCard(AcsDeviceCardInfo cardInfo) {
        AcsDeviceResult result = new AcsDeviceResult(false,"使能卡片失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        result = this.v6.enableOneCard(cardInfo.getControllerIndex(), cardInfo.getCardIndex(), cardInfo.getEnable());
        return result;
    }

    @Override
    public AcsDeviceResult CancelUser(AcsDeviceUserInfo userInfo) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "注销用户成功");
    }

    @Override
    public AcsDeviceResult CancelCard(AcsDeviceCardInfo cardInfo) {
        AcsDeviceResult result = new AcsDeviceResult(false,"删除卡片失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        result = this.v6.deleteOneCard(cardInfo.getControllerIndex(), cardInfo.getCardIndex());
        return result;
    }

    @Override
    public AcsDeviceResult CancelFace(AcsDeviceFaceInfo faceInfo) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "注销人脸成功");
    }

    @Override
    public AcsDeviceResult DoorControl(AcsDeviceCommandInfo cmd) {
        log.info(this.v6.getIp() + "远程控制");
        AcsDeviceResult result = new AcsDeviceResult(false,"门控制失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        switch(cmd.getCmd()){
        //关门
        case 0:
            result = this.v6.remoteControl(cmd.getControllerIndex(),String.format("%02d", cmd.getDoor()),"0","0015");
            break;
        //开门
        case 1:
            result = this.v6.remoteControl(cmd.getControllerIndex(),String.format("%02d", cmd.getDoor()),"1","0015");
            break;
        //常开
        case 2:
            result = this.v6.remoteControl(cmd.getControllerIndex(),String.format("%02d", cmd.getDoor()),"1","9999");
            break;
        //常闭
        case 3:
            result = this.v6.remoteControl(cmd.getControllerIndex(),String.format("%02d", cmd.getDoor()),"0","9999");
            break;
        //恢复
        case 4:
            result = this.v6.resumeDoor(cmd.getControllerIndex());
        }
        log.info(this.v6.getIp() +"远程控制结束");
        return result;
    }

    @Override
    public AcsDeviceResult SetupAlarmChan() {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "布防成功");
    }

    @Override
    public AcsDeviceResult CloseAlarmChan() {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "撤防成功");
    }

    @Override
    public AcsDeviceResult CheckConnect(String controllerIndex) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return this.v6.getData(controllerIndex);
    }

    @Override
    public AcsDeviceResult SetWeekTemplate(AcsDeviceWeekTemplateInfo weekTemplate) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "设置周计划模板成功");
    }

    @Override
    public AcsDeviceResult SetHolidayGroup(AcsDeviceHolidayGroupInfo holidayGroup) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "设置假日组成功");
    }

    @Override
    public AcsDeviceResult SetHolidayPlan(AcsDeviceHolidayPlanInfo[] holidayPlan) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return new AcsDeviceResult(true, "设置假日计划成功");
    }
    
    @Override
    public AcsDeviceResult setAppgroup(AcsDeviceAppGroupSettingInfo[] settingList){
        AcsDeviceResult result = new AcsDeviceResult(false,"下载应用群组失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        for(AcsDeviceAppGroupSettingInfo appGroupSetting:settingList){
            result = this.v6.setOtherSetting(appGroupSetting.getControllerIndex(),appGroupSetting.getAppId(),appGroupSetting.getReader(),appGroupSetting.getOut(),appGroupSetting.getSecPinTz(),appGroupSetting.getPerPinTz());
            if(!result.isCode()){
                return result;
            }
            result = this.v6.setholidaySetting(appGroupSetting.getControllerIndex(), appGroupSetting.getAppId(), appGroupSetting.getHolidayTz(), appGroupSetting.getHolidayOut());
            if(!result.isCode()){
                return result;
            }
            result = this.v6.setweekSetting(appGroupSetting.getControllerIndex(), appGroupSetting.getAppId(), appGroupSetting.getWeekTz(), appGroupSetting.getWeekOut());
            if(!result.isCode()){
                return result;
            }
        }
        result.setCode(true);
        result.setMessage("下载应用群组成功");
        return result;
    }

    @Override
    public AcsDeviceResult setTimezone(AcsDeviceTimeZoneInfo[] timeZoneList){
        AcsDeviceResult result = new AcsDeviceResult(false,"下载时区失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        log.info("V6开始下载时区");
        for(AcsDeviceTimeZoneInfo timeZoneSetting:timeZoneList){
            result = this.v6.setTimeZoom(timeZoneSetting.getControllerIndex(), timeZoneSetting.getNn(), timeZoneSetting.getTimeZone());
            if(!result.isCode()){
                return result;
            }
        }
        log.info("V6下载时区结束");
        result.setCode(true);
        result.setMessage("下载时区成功");
        return result;
    }

    @Override
    public AcsDeviceResult setInterval(AcsDeviceTimeIntervalInfo[] timeIntervalList){
        AcsDeviceResult result = new AcsDeviceResult(false,"下载时段失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        log.info("V6开始下载时段");
        for(AcsDeviceTimeIntervalInfo timeIntervalSetting:timeIntervalList){
            if(timeIntervalSetting.getNn().equals("00") || timeIntervalSetting.getNn().equals("31")){
                continue;
            }
            result = this.v6.setInterval(timeIntervalSetting.getControllerIndex(), timeIntervalSetting.getNn(), timeIntervalSetting.getStart(), timeIntervalSetting.getEnd()); 
            if(!result.isCode()){
                return result;
            }
        }
        log.info("V6下载时段结束");
        result.setCode(true);
        result.setMessage("下载时段成功");
        return result;
    }

    @Override
    public AcsDeviceResult setHoliday(AcsDeviceHolidayInfo[] holidayList){
        AcsDeviceResult result = new AcsDeviceResult(false,"下载假日失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        log.info("V6开始下载假日");
        for(AcsDeviceHolidayInfo holidaySetting:holidayList){
            result = this.v6.setHoliday(holidaySetting.getControllerIndex(), holidaySetting.getMm(), holidaySetting.getH()); 
            if(!result.isCode()){
                return result;
            }
        }
        log.info("V6下载假日结束");
        result.setCode(true);
        result.setMessage("下载假日成功");
        return result;
    }

    @Override
    public AcsDeviceResult getCardNumNoInSystem(String  controllerIndex,String nn){
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return this.v6.getCardNumNoInSystem(controllerIndex, nn);
    }

    @Override
    public AcsDeviceResult getDoorStatus(String controllerIndex){
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return this.v6.getDoorStatus(controllerIndex);
    }

    @Override
    public AcsDeviceResult getRecordAndTotal(String controllerIndex){
        AcsDeviceResult result = new AcsDeviceResult(false,"获取记录失败");
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        result = this.v6.getRecordAndTotal(controllerIndex);
        if(!result.isCode()){
            return result;
        }
        this.v6.deleteOneRecord(controllerIndex);
        return result;
    }

    @Override
    public AcsDeviceResult setCardNumber(String controllerIndex, String cardIndex, String cardNumber) {
        if(this.v6.getV6Socket() == null){
            return new AcsDeviceResult(false, "控制器未连接");
        }
        return this.v6.setCardNumOneCard(controllerIndex, cardIndex, cardNumber);
    }
}
