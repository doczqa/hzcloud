package com.hzcloud.group.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 假日计划对象 acs_holiday_plan
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
public class AcsHolidayPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long planId;

    /** 假日计划名称 */
    @Excel(name = "假日计划名称")
    private String planName;

    /** 起始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "时段一起", width = 30, dateFormat = "HH:mm:ss")
    private Date startTime1;

    /** 时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "时段一止", width = 30, dateFormat = "HH:mm:ss")
    private Date endTime1;

    /** 时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "时段二起", width = 30, dateFormat = "HH:mm:ss")
    private Date startTime2;

    /** 时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "时段二止", width = 30, dateFormat = "HH:mm:ss")
    private Date endTime2;

    /** 时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "时段三起", width = 30, dateFormat = "HH:mm:ss")
    private Date startTime3;

    /** 时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "时段三止", width = 30, dateFormat = "HH:mm:ss")
    private Date endTime3;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setPlanName(String planName) 
    {
        this.planName = planName;
    }

    public String getPlanName() 
    {
        return planName;
    }
    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }
    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }
    public void setStartTime1(Date startTime1) 
    {
        this.startTime1 = startTime1;
    }

    public Date getStartTime1() 
    {
        return startTime1;
    }
    public void setEndTime1(Date endTime1) 
    {
        this.endTime1 = endTime1;
    }

    public Date getEndTime1() 
    {
        return endTime1;
    }
    public void setStartTime2(Date startTime2) 
    {
        this.startTime2 = startTime2;
    }

    public Date getStartTime2() 
    {
        return startTime2;
    }
    public void setEndTime2(Date endTime2) 
    {
        this.endTime2 = endTime2;
    }

    public Date getEndTime2() 
    {
        return endTime2;
    }
    public void setStartTime3(Date startTime3) 
    {
        this.startTime3 = startTime3;
    }

    public Date getStartTime3() 
    {
        return startTime3;
    }
    public void setEndTime3(Date endTime3) 
    {
        this.endTime3 = endTime3;
    }

    public Date getEndTime3() 
    {
        return endTime3;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("planId", getPlanId())
            .append("planName", getPlanName())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("startTime1", getStartTime1())
            .append("endTime1", getEndTime1())
            .append("startTime2", getStartTime2())
            .append("endTime2", getEndTime2())
            .append("startTime3", getStartTime3())
            .append("endTime3", getEndTime3())
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
