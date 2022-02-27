package com.hzcloud.device.config;

import com.hzcloud.device.AcsDeviceController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备控制器状态机
 */
public class DeviceConStatus {

    // 设备状态机 key: 设备ID value: AcsDeviceController对象
    private volatile static Map<String, AcsDeviceController> DEVICE = new ConcurrentHashMap<>();

    /**
     * 添加控制器
     * @param ip    控制器IP
     * @param deviceCon 控制器对象
     */
    public static void addDeviceController(String ip, AcsDeviceController deviceCon) {
        DEVICE.put(ip, deviceCon);
    }

    /**
     * 移除控制器
     * @param ip 控制器IP
     */
    public static void removeDeviceController(String ip) {
        DEVICE.remove(ip);
    }

    /**
     * 获取控制器
     * @param ip 控制器IP
     * @return
     */
    public static AcsDeviceController getDeviceController(String ip) {
        return DEVICE.get(ip);
    }

    /**
     * 获取Map
     */
    public static Map<String, AcsDeviceController> getMap(){
        return DEVICE;
    }
}
