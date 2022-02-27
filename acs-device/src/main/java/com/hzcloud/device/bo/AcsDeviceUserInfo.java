package com.hzcloud.device.bo;

import java.util.Date;

import lombok.Data;

//下发的用户信息
@Data
public class AcsDeviceUserInfo {
    //用户名
    private String userName;
    //用户号
    private Long userNumber;
    //卡号
    private String cardNumber;
    //有效期起
    private Date  beginTime;
    //有效期止
    private Date  endTime;
    //使能  
    private Boolean enable;
    //门权限
    private Integer doorRight;
    //计划模板组
    private Integer PlanTemplateNumber;
}
