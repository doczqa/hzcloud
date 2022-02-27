package com.hzcloud.group.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 门禁组添加门BO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscDoorGroupAddDoorBo {

    /**
     * 门禁组ID
     */
    @NotNull
    private Long groupId;
    /**
     * 门ID数组
     */
    @NotNull
    private Long[] doorList;
}
