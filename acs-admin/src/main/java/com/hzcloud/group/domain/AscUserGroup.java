package com.hzcloud.group.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 用户组对象 asc_user_group
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscUserGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long groupId;

    /** 用户组名称 */
    @Excel(name = "用户组名称")
    private String groupName;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志 */
    private String delFlag;

    /**
     * 用户组中用户ID数组
     */
    private Long[] userIds;

    /**
     * 前段临时字段
     */
    private boolean seen = false;

    /**
     * 是否被分配到门禁组,大于0代表已分配
     */
    private Integer su;

    /**
     * 门禁组ID
     */
    private Long doorGroupId;

}
