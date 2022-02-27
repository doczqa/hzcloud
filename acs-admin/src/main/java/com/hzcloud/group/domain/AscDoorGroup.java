package com.hzcloud.group.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 门禁组对象 asc_door_group
 *
 * @author jarrymei
 * @date 2021-04-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AscDoorGroup extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long groupId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String groupName;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 周模板计划id
     */
    private Long weekTemplateId;
    
    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;
}
