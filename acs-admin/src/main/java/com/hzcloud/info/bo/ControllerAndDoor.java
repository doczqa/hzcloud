package com.hzcloud.info.bo;

import java.util.List;

import lombok.Data;

@Data
public class ControllerAndDoor {
    //控制器ids
    private List<Long> controllerIds;
    //门端口
    private Long doorPin;
}
