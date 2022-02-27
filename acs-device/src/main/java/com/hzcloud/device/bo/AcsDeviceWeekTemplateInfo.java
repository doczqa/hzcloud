package com.hzcloud.device.bo;

import java.sql.Time;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceWeekTemplateInfo {
    //周计划模板id
    private Long weekTemplateNumber;

    //假日组id
    private Long holidayGroupNumber;

    //周计划模板名称
    private String weekTemplateName;

    //周时间段
    private AcsDeviceDayTime[] weekTime ;

    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
                .append("weekTemplateNumber", getWeekTemplateNumber())
                .append("holidayGroupNumber", getHolidayGroupNumber())
                .append("weekTemplateName", getWeekTemplateName())
                .append("weekTime",getWeekTime())
               .toString();
    }
}
