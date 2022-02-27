package com.hzcloud.device.bo;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceHolidayGroupInfo {
    //假日组id
    private Long holidayGroupNumber;
    
    //假日计划id组
    private Long[] holidayPlanNumbers;

    //假日组名称
    private String holidayGroupName;

    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
                .append("holidayGroupNumber", getHolidayGroupNumber())
                .append("holidayPlanNumbers", getHolidayPlanNumbers())
                .append("holidayGroupName",getHolidayGroupName())
               .toString();
    }
}
