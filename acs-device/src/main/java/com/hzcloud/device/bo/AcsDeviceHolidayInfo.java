package com.hzcloud.device.bo;

import com.alibaba.fastjson.annotation.JSONField;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceHolidayInfo {
    private String controllerIndex;
    private String mm;
    private String h;
    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
        .append("controllerIndex", getControllerIndex())
        .append("mm", getMm())
        .append("h",getH())
       .toString();
    }
}
