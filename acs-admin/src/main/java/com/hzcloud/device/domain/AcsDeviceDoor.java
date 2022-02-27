package com.hzcloud.device.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 门禁门对象 acs_device_door
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcsDeviceDoor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long doorId;

    /** 门名称 */
    @Excel(name = "门名称")
    private String doorName;

    /** 控制器ID */
    @Excel(name = "控制器ID")
    private Long controllerId;

    /** 控制器名称 */
    @Excel(name = "控制器")
    private String controllerName;

    /** 端口 */
    @Excel(name = "端口")
    private Integer pin;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 门禁组ID*/
    private Long groupId;

    /** 标识符 */
    private String identifier;
}
