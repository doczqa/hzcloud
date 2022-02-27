package com.hzcloud.device.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class IssueUserData {
    private String  userName;
    private Long  userId;
    private String cardNumber;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Integer planTemplateNumber;
    private String faceInfo;
    private Boolean enable;

    private String controllerIndex;
    private String cardIndex;

    @Override
    public String toString(){
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
        return new ToStringBuilder(this,ToStringStyle.JSON_STYLE)
                .append("userName", getUserName())
                .append("userId", getUserId())
                .append("cardNumber", getCardNumber())
                .append("beginTime", formatter.format(getBeginTime()))
                .append("endTime", formatter.format(getEndTime()))
                .append("planTemplateNumber", getPlanTemplateNumber())
                .append("faceInfo", getFaceInfo())
                .append("enable", getEnable())
                .append("controllerIndex", getControllerIndex())
                .append("cardIndex", getCardIndex())
               .toString();
    }
}
