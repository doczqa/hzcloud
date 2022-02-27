package com.hzcloud.device.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.annotation.Excel.Type;
import com.hzcloud.common.core.domain.entity.SysDept;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 区域对象 acs_device_zone
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
public class AcsDeviceZone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long zoneId;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String zoneName;

    /** 所属部门 */
    @Excel(name = "所属部门")
    private Long deptId;

    @Excel(name = "所属部门")
    private String deptName;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 控制器组 */
    private Long[] controllerIds;

    public void setZoneId(Long zoneId) 
    {
        this.zoneId = zoneId;
    }

    public Long getZoneId() 
    {
        return zoneId;
    }
    public void setZoneName(String zoneName) 
    {
        this.zoneName = zoneName;
    }

    public String getZoneName() 
    {
        return zoneName;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public Long[] getControllerIds()
    {
        return controllerIds;
    }

    public void setControllerIds(Long[] controllerIds)
    {
        this.controllerIds = controllerIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("zoneId", getZoneId())
            .append("zoneName", getZoneName())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
