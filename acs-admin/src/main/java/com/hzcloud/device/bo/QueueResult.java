package com.hzcloud.device.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下发结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueResult {

    /**
     * 结果 0:成功 1:失败
     */
    private String code;
    /**
     * 成功|失败信息
     */
    private String message;
}
