package com.hzcloud.group.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcsUserGroupUpdateUserBo {

    /**
     * 用户组ID
     */
    private Long groupId;
    /**
     * 用户ID数组
     */
    private Long[] userIds;
    /**
     * 类型　1:移除 2:添加
     */
    private Integer type;
}
