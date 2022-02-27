package com.hzcloud.device.bo;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceDayTime {
    //每日时间段
    private AcsDeviceTimeInterval[] times;

    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
        .append("times", getTimes())
        .toString();
    }
}
