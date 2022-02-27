package com.hzcloud.device.bo;

import lombok.Data;

@Data
public class AcsDeviceCardInfo {
    //v6控制器索引
    private String controllerIndex;
    //V6卡索引
    private String  cardIndex;
    //卡号
    private String  cardNumber;
    //门权限
    private Integer doorRight;
    //APB设置
    private String  APB;
    //使能
    private String  enable;
}
