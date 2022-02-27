package com.hzcloud.device.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.annotation.Excel.Type;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 门禁控制器对象 acs_device_controller
 * 
 * @author 张帆
 * @date 2021-04-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcsDeviceCon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long controllerId;

    /** 控制器名 */
    @Excel(name = "控制器名")
    private String controllerName;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ip;

    /** 端口 */
    @Excel(name = "端口")
    private Long port;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String type;

    /** 状态 */
    @Excel(name = "状态", type = Type.EXPORT)
    private String status;

    /** 布防状态 */
    @Excel(name = "布防状态", type = Type.EXPORT)
    private String alarmStatus;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 标识符
     */
    private String identifier;

    /**
     * 控制器索引号
     */
    private String controllerIndex;

    /**
     * 卡容量
     */
    private Integer capacity;
}
