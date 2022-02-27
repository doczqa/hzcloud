package com.hzcloud.device.service;

import com.hzcloud.device.vo.QueueStatusVo;

import java.util.List;

public interface IDeviceControllerQueueService {

    /**
     * 执行命令下发业务
     * @param controllerId　控制器ID
     * @param command   命令
     */
    void execute(Long controllerId, String ip, Integer[] command);

    /**
     * 队列状态
     * @return
     */
    List<QueueStatusVo> queueStatus();

    /**
     * 
     */
    void  replay();
}
