package com.hzcloud.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户穿梭框VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransferVo {

    /**
     * 主键
     */
    private Long key;
    /**
     * 标题
     */
    private String label;
    /**
     * 是否禁用 0:禁用
     */
    private Boolean disabled;
}
