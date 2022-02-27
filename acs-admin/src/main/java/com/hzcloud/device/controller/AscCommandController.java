package com.hzcloud.device.controller;

import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.device.bo.AscCommandSendBo;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 命令操作Controller
 */
@RestController
@RequestMapping("/command")
public class AscCommandController {

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;

    /**
     * 发送命令
     * @param commandSendBo
     * @return
     */
    @PostMapping
    public AjaxResult send(@Validated @RequestBody AscCommandSendBo commandSendBo) {
        deviceControllerQueueService.execute(commandSendBo.getControllerId(),commandSendBo.getIp(), commandSendBo.getCommand());
        return AjaxResult.success();
    }

    /**
     * 获取队列状态
     * @return
     */
    @GetMapping("/queueStatus")
    public AjaxResult queueStatus() {
        return AjaxResult.success(deviceControllerQueueService.queueStatus());
    }
}
