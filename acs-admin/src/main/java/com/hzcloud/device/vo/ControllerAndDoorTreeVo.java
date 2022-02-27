package com.hzcloud.device.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControllerAndDoorTreeVo {

    /**
     * ID
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型 0:控制器 1:门
     */
    private String type;
    /**
     * 状态
     */
    private String status;
    /**
     * 是否有子节点
     */
    private Boolean hasChildren;
}
