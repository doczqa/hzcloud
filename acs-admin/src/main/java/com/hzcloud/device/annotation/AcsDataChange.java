package com.hzcloud.device.annotation;

import com.hzcloud.device.enums.ControllerCommandEnum;

import java.lang.annotation.*;

/**
 *　门禁数据改变
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AcsDataChange {

    /**
     * 命令
     * @return
     */
    ControllerCommandEnum value();

}
