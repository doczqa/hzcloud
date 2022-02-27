package com.hzcloud.v6.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 假日设置对象 acs_v6_holiday
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
public class AcsV6Holiday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 月份 */
    @Excel(name = "月份")
    private String month;

    /** 假日 */
    @Excel(name = "假日")
    private String holidays;

    public void setMonth(String month) 
    {
        this.month = month;
    }

    public String getMonth() 
    {
        return month;
    }
    public void setHolidays(String holidays) 
    {
        this.holidays = holidays;
    }

    public String getHolidays() 
    {
        return holidays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("month", getMonth())
            .append("holidays", getHolidays())
            .toString();
    }
}
