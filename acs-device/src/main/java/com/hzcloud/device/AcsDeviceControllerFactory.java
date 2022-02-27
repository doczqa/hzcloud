package com.hzcloud.device;

import com.hzcloud.common.enums.DeviceControllerTypeEnum;
import com.hzcloud.device.hikvision.K1T604TM;
import com.hzcloud.device.syris.V6Controller;
import com.hzcloud.device.syris.V8Controller;

public class AcsDeviceControllerFactory {

    /**
     *
     * @param type 类型为DeviceControllerTypeEnum的code
     * @return
     */
    public static AcsDeviceController CreateController(String type){
        AcsDeviceController controller = null;
        if (DeviceControllerTypeEnum.TYPE1.getCode().equals(type)){
            controller = new K1T604TM();
        }
        if(DeviceControllerTypeEnum.TYPE2.getCode().equals(type)){
            controller = new V8Controller();
        }
        if(DeviceControllerTypeEnum.TYPE3.getCode().equals(type)){
            controller = new V6Controller();
        }
        return controller;
    }
}
