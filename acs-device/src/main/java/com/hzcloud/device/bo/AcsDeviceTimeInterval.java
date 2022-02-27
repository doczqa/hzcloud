package com.hzcloud.device.bo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceTimeInterval {
    // 起始时间
    private String  beginTime;
    // 结束时间
    private String endTime;

    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
        .append("beginTime", getBeginTime())
        .append("endTime", getEndTime())
       .toString();
    }
}
