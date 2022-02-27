package com.hzcloud.device.bo;

import lombok.Data;

@Data
public class AcsDeviceCommandInfo {
    private String controllerIndex;
    private int door;
    private int cmd; 
}
