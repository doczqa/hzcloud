package com.hzcloud.group.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 門禁組添加用戶組BO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscDoorGroupAddUserGroupBo {

    /**
     * 門禁组ID
     */
    @NotNull
    private Long doorGroupId;
    /**
     * 用户組ID
     */
    @NotNull
    private Long[] userGroupIds;
}
