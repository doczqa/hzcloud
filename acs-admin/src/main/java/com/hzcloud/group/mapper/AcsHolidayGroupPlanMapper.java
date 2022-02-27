package com.hzcloud.group.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hzcloud.group.domain.AcsHolidayGroupPlan;

/**
 * 假日组与假日计划关联表 数据层
 * 
 * @author zhangfan
 */
public interface AcsHolidayGroupPlanMapper {
    /**
     * 通过组ID删除假日组和假日计划关联
     * 
     * @param groupId 用户ID
     * @return 结果
     */
    public int deleteGroupPlanByGroupId(Long groupId);

    /**
     * 批量删除假日组和假日计划关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGroupPlan(Long[] ids);

    /**
     * 通过假日计划ID查询假日计划使用数量
     * 
     * @param planId 假日计划ID
     * @return 结果
     */
    public int countGroupPlanByPlanId(Long planId);

    /**
     * 批量新增假日组假日计划信息
     * 
     * @param groupPlanList 假日组假日计划列表
     * @return 结果
     */
    public int batchGroupPlan(List<AcsHolidayGroupPlan> groupPlanList);

    /**
     * 删除假日组和假日计划关联信息
     * 
     * @param groupPlan 假日组和假日计划关联信息
     * @return 结果
     */
    public int deleteGroupPlanInfo(AcsHolidayGroupPlan groupPlan);

    /**
     * 批量取消假日组假日计划
     * 
     * @param planId 假日计划ID
     * @param groupIds 需要删除的假日组数据ID
     * @return 结果
     */
    public int deleteGroupPlanInfos(@Param("planId") Long planId, @Param("groupIds") Long[] groupIds);
}
