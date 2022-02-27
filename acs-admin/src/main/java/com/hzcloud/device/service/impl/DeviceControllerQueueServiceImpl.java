package com.hzcloud.device.service.impl;

import com.alibaba.fastjson.JSON;
import com.hzcloud.common.enums.QueueStatus;
import com.hzcloud.device.bo.Payload;
import com.hzcloud.device.bo.QueueResult;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.device.service.IAcsCommandQueueService;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.device.vo.QueueStatusVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 每一个门禁控制器都会初始化一个线程池并执行相关业务
 *
 * @author jarrymei
 */
@Slf4j
@Service
public class DeviceControllerQueueServiceImpl implements IDeviceControllerQueueService {

    @Autowired
    private IAcsCommandQueueService commandQueueService;
    @Autowired
    private IAcsDeviceConService deviceConService;

    private Map<String, ConsumerThread> map = new ConcurrentHashMap<>();

    @Override
    public void execute(Long controllerId, String ip, Integer[] command) {
        this.executeConsumer(ip);
        this.executeProduct(controllerId, command);
    }

    @Override
    public void replay(){
        this.executeReplay();
        List<AcsDeviceCon> list = deviceConService.selectAcsDeviceConList(null);
        for(AcsDeviceCon controller: list){
            this.executeConsumer(controller.getIp());
        }
    }

    @Override
    public List<QueueStatusVo> queueStatus() {
        return QueueUtils.status();
    }

    private void executeProduct(Long controllerId, Integer[] command) {
        new Thread(new ProductThread(controllerId, command)).start();
    }

    private void executeReplay(){
        new Thread(new ReplayThread()).start();
    }

    /**
     * @param controllerId  控制器ID
     * @param command   命令
     */
    private void executeConsumer(String ip) {
        ConsumerThread newThread = new ConsumerThread(ip);
        ConsumerThread oldThread = map.get(ip);
        if (oldThread != null && oldThread.equals(newThread)) {
            log.info("控制器IP:{} 的消费线程已存在", ip);
            return;
        }
        log.info("创建控制器IP:{}的消费线程", ip);
        map.put(ip, newThread);
        new Thread(newThread).start();
    }

    /**
     * 
     */
    private class ReplayThread implements Runnable{
        @Override
        public void run(){
            log.info("启动重试的生产线程");
            // 查询待下发的数据
            List<AcsCommandQueue> list = commandQueueService.selectAcsCommandQueueList(null);
            if (list == null || list.size() < 1) {
                return;
            }
            for (AcsCommandQueue commandQueue : list) {
                Payload payload = new Payload(
                        commandQueue.getTaskId(),
                        commandQueue.getControllerId(),
                        commandQueue.getIp(),
                        commandQueue.getDoorPin(),
                        commandQueue.getCommand(),
                        JSON.parse(commandQueue.getData()),
                        commandQueue.getCreateTime() );
                QueueUtils.put(payload);
                commandQueue.setStatus(QueueStatus.EXECUTED.code);
                commandQueue.setIssueTime(new Date());
                commandQueue.setResultCode("2");
                commandQueueService.updateAcsCommandQueue(commandQueue);
            }
        }
    }

    /**
     * 生产线程
     */
    private class ProductThread implements Runnable {

        private volatile Long controllerId;
        private volatile Integer[] command;

        public ProductThread(Long controllerId, Integer[] command) {
            this.controllerId = controllerId;
            this.command = command;
        }

        @Override
        public void run() {
            log.info("启动控制器ID:{}的生产线程", controllerId);
            
            // 查询待下发的数据
            List<AcsCommandQueue> list = commandQueueService.selectAcsCommandQueueListByCmd(controllerId, command);

            if (list == null || list.size() < 1) {
                return;
            }
            for (AcsCommandQueue commandQueue : list) {
                Payload payload = new Payload(
                        commandQueue.getTaskId(),
                        commandQueue.getControllerId(),
                        commandQueue.getIp(),
                        commandQueue.getDoorPin(),
                        commandQueue.getCommand(),
                        JSON.parse(commandQueue.getData()),
                        commandQueue.getCreateTime() );
                QueueUtils.put(payload);
                commandQueue.setStatus(QueueStatus.EXECUTED.code);
                commandQueue.setIssueTime(new Date());
                commandQueueService.updateAcsCommandQueue(commandQueue);
            }
        }
    }

    /**
     * 消费线程
     */
    private class ConsumerThread implements Runnable {

        private volatile String ip;
        

        public ConsumerThread(String ip) {
            this.ip = ip;
        }

        /**
         * 重写equals方法，用来判断以controllerId为比较条件的消费线程是否存在
         * @param obj
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ConsumerThread) {
                if (((ConsumerThread) obj).ip == this.ip) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void run() {
            log.info("启动控制器ID:{}的消费线程", ip);
            while (true) {
                Payload payload = QueueUtils.take(ip);
                QueueResult result = null;
                switch (ControllerCommandEnum.getInstance(payload.getCommand())) {
                    case ISSUED_USER_INFO:
                        result = ControllerCommandEnum.ISSUED_USER_INFO.run(payload);
                        break;
                    case ENABLE_USER_INFO:
                        result = ControllerCommandEnum.ENABLE_USER_INFO.run(payload);
                        break;
                    case DELETE_USER_INFO:
                        result = ControllerCommandEnum.DELETE_USER_INFO.run(payload);
                        break;
                    case CANCEL_USER_INFO:
                        result = ControllerCommandEnum.CANCEL_USER_INFO.run(payload);
                        break;
                    case ADD_TEMPLATE:
                        result = ControllerCommandEnum.ADD_TEMPLATE.run(payload);
                        break;
                    case EDIT_TEMPLATE:
                        result = ControllerCommandEnum.EDIT_TEMPLATE.run(payload);
                        break;
                    case DELETE_TEMPLATE:
                        result = ControllerCommandEnum.DELETE_TEMPLATE.run(payload);
                        break;
                    case ADD_HOLIDAY_PLAN:
                        result = ControllerCommandEnum.ADD_HOLIDAY_PLAN.run(payload);
                        break;
                    case EDIT_HOLIDAY_PLAN:
                        result = ControllerCommandEnum.EDIT_HOLIDAY_PLAN.run(payload);
                        break;
                    case DELETE_HOLIDAY_PLAN:
                        result = ControllerCommandEnum.DELETE_HOLIDAY_PLAN.run(payload);
                        break;
                    case ADD_HOLIDAY_GROUP:
                        result = ControllerCommandEnum.ADD_HOLIDAY_GROUP.run(payload);
                        break;
                    case EDIT_HOLIDAY_GROUP:
                        result = ControllerCommandEnum.EDIT_HOLIDAY_GROUP.run(payload);
                        break;
                    case DELETE_HOLIDAY_GROUP:
                        result = ControllerCommandEnum.DELETE_HOLIDAY_GROUP.run(payload);
                        break;
                    case ISSUED_APPGROUP:
                        result = ControllerCommandEnum.ISSUED_APPGROUP.run(payload);
                        break;
                    case ISSUED_DATEANDTIME:
                        result = ControllerCommandEnum.ISSUED_DATEANDTIME.run(payload);
                        break;
                    default:
                        log.warn("未知命令");
                        break;
                }

                if (result != null ) {
                    commandQueueService.updateAcsCommandQueueResult(payload.getTaskId(), result.getCode(), result.getMessage());
                }
            }
        }
    }

}
