package com.hzcloud.common.enums;

/**
 * 门禁控制器类型
 */
public enum DeviceControllerTypeEnum {

    TYPE1("1", "海康门禁控制器"),
    TYPE2("2", "玺瑞V8控制器"),
    TYPE3("3", "玺瑞V6控制器");

    private final String code;
    private final String info;

    DeviceControllerTypeEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }

}
