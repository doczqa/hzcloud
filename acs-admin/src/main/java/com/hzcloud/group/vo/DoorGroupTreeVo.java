package com.hzcloud.group.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 门禁组树VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoorGroupTreeVo {

    /** 节点ID*/
    private Integer id;
    /** 标题*/
    private String label;
    /** */
    private Boolean leaf;

}
