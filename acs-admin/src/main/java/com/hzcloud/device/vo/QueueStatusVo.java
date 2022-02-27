package com.hzcloud.device.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueStatusVo {

    /**
     * 控制器ip
     */
    private String ip;
    /**
     * 队列剩余大小
     */
    private Integer size;
}
