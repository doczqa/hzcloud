package com.hzcloud.group.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 用户组和用户关联对象 asc_door_group_user_group
 * 
 * @author ruoyi
 * @date 2021-04-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscDoorGroupUserGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 门禁组ID,关联asc_device_door_group表group_id字段, */
    private Long doorGroupId;

    /** 用户组ID,关联asc_user_group表group_id字段 */
    private Long userGroupId;

}
