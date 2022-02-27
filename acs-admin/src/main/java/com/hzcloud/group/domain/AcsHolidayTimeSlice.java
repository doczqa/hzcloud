package com.hzcloud.group.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 假日时间段对象 acs_holiday_time_slice
 * 
 * @author zhangfan
 * @date 2021-06-16
 */
public class AcsHolidayTimeSlice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**ID */
    private Long id;

    /** 时段一起 */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime1;

    /** 时段一止 */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date endTime1;

    /** 时段二起 */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime2;

    /** 时段二止 */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date endTime2;

    /** 时段三起 */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date startTime3;

    /** 时段三止 */
    @JsonFormat(pattern = "HH:mm:ss")
    private Date endTime3;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId(){
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("startTime1", getStartTime1())
            .append("endTime1", getEndTime1())
            .append("startTime2", getStartTime2())
            .append("endTime2", getEndTime2())
            .append("startTime3", getStartTime3())
            .append("endTime3", getEndTime3())
            .toString();
    }
}
