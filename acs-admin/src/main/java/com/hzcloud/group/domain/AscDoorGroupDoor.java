package com.hzcloud.group.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 门禁组和门关联对象 asc_door_group_door
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscDoorGroupDoor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 门禁组ID,关联asc_device_door_group表group_id字段 */
    private Long doorGroupId;

    /** 门ID,关联asc_device_door表door_id字段 */
    private Long doorId;

    private Long controllerId;
}
