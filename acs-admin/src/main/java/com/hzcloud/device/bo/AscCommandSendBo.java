package com.hzcloud.device.bo;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AscCommandSendBo {

    /**
     * 控制器ID
     */
    @NotNull(message = "控制器ID不能为空")
    private Long controllerId;

    /**
     * 控制器IO
     */
    @NotNull(message = "控制器IP不能为空")
    private String ip;

    /**
     * 命令，参数范围参考ControllerCommandEnum
     */
    @NotNull(message = "命令不能为空")
    //@EnumValue(enumClass = ControllerCommandEnum.class, enumMethod = "isValidName", message = "命令不正确")
    private Integer[] command;

}
