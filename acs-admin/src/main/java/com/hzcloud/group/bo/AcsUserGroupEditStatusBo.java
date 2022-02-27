package com.hzcloud.group.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcsUserGroupEditStatusBo {

    /**
     * 用户组ID
     */
    @NotNull(message = "用户组ID不能为空")
    private Long groupId;
    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    private String status;
}
