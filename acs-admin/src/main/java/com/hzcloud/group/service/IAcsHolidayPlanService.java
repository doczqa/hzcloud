package com.hzcloud.group.service;

import java.util.List;
import com.hzcloud.group.domain.AcsHolidayPlan;

/**
 * 假日计划Service接口
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
public interface IAcsHolidayPlanService 
{
    /**
     * 查询假日计划
     * 
     * @param planId 假日计划ID
     * @return 假日计划
     */
    public AcsHolidayPlan selectAcsHolidayPlanById(Long planId);

    /**
     * 查询假日计划列表
     * 
     * @param acsHolidayPlan 假日计划
     * @return 假日计划集合
     */
    public List<AcsHolidayPlan> selectAcsHolidayPlanList(AcsHolidayPlan acsHolidayPlan);

    /**
     * 新增假日计划
     * 
     * @param acsHolidayPlan 假日计划
     * @return 结果
     */
    public int insertAcsHolidayPlan(AcsHolidayPlan acsHolidayPlan);

    /**
     * 修改假日计划
     * 
     * @param acsHolidayPlan 假日计划
     * @return 结果
     */
    public int updateAcsHolidayPlan(AcsHolidayPlan acsHolidayPlan);

    /**
     * 批量删除假日计划
     * 
     * @param planIds 需要删除的假日计划ID
     * @return 结果
     */
    public int deleteAcsHolidayPlanByIds(Long[] planIds);

    /**
     * 删除假日计划信息
     * 
     * @param planId 假日计划ID
     * @return 结果
     */
    public int deleteAcsHolidayPlanById(Long planId);

    /**
     * 根据假日组ID获取假日计划列表
     * 
     * @param groupId 区域ID
     * @return 假日计划ID列表
     */
    public List<Long> selectPlanListByGroupId(Long groupId);
}
