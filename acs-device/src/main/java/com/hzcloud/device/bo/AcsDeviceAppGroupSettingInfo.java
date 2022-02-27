package com.hzcloud.device.bo;

import javax.management.loading.PrivateClassLoader;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class AcsDeviceAppGroupSettingInfo {
    private String controllerIndex;
    private String appId;
    private String reader;
    private String out;
    private String secPinTz;
    private String perPinTz;
    private String holidayTz;
    private String holidayOut;
    private String weekTz;
    private String weekOut;
    @Override
    public String toString(){
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
        .append("controllerIndex", getControllerIndex())
        .append("appId", getAppId())
        .append("reader",getReader())
        .append("out",getOut())
        .append("secPinTz", getSecPinTz())
        .append("perPinTz",getPerPinTz())
        .append("holidayTz",getHolidayTz())
        .append("holidayOut", getHolidayOut())
        .append("weekTz",getWeekTz())
        .append("weekOut",getWeekOut())
       .toString();
    }
}
