package com.hzcloud.device.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceHolidayPlanInfo {
    //假日计划id
    private Long holidayPlanNumber;

    //假日起始日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    //假日截止日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    //假日时间段
    private AcsDeviceTimeInterval[] times ;

    @Override
    public String toString(){
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
                .append("holidayPlanNumber", getHolidayPlanNumber())
                .append("startDate", formatter.format(getStartDate()))
                .append("endDate", formatter.format(getEndDate()))
                .append("times",getTimes())
               .toString();
    }
}
