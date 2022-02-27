package com.hzcloud.group.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 移除门BO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscDoorGroupRemoveDoorBo {

    /**
     * 门ID
     */
    @NotNull
    private Long doorId;
    /**
     * 门禁组ID
     */
    @NotNull
    private Long groupId;

}
