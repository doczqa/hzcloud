package com.hzcloud.group.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 用户组和用户关联对象 asc_user_group_user
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
public class AscUserGroupUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户组ID,关联asc_user_group表group_id字段 */
    private Long userGroupId;

    /** 用户ID,关联sys_user表user_id字段 */
    private Long userId;

    public void setUserGroupId(Long userGroupId) 
    {
        this.userGroupId = userGroupId;
    }

    public Long getUserGroupId() 
    {
        return userGroupId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userGroupId", getUserGroupId())
            .append("userId", getUserId())
            .toString();
    }
}
