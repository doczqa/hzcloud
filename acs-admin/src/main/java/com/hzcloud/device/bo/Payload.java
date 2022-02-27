package com.hzcloud.device.bo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 门禁控制器下发参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payload {
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 门禁控制器ID
     */
    private Long controllerId;

    /**
     * IP
     */
    private String ip;

    /**
     * 门Pin
     */
    private Integer doorPin;

    /**
     * 命令
     */
    private Integer command;

    /**
     * 数据
     */
    private Object data;
    /**
     * 创建时间
     */
    private Date createTime;
}
