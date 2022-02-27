package com.hzcloud.device.bo;

import com.alibaba.fastjson.annotation.JSONField;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceTimeIntervalInfo {
    private String controllerIndex;

    private String nn;

    private String start;
    private String end;
    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
        .append("controllerIndex", getControllerIndex())
        .append("nn", getNn())
        .append("start",getStart())
        .append("end",getEnd())
       .toString();
    }
}
