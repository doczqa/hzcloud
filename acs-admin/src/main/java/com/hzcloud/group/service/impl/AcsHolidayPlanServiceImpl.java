package com.hzcloud.group.service.impl;

import java.util.List;
import com.hzcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.group.mapper.AcsHolidayPlanMapper;
import com.hzcloud.group.domain.AcsHolidayPlan;
import com.hzcloud.group.service.IAcsHolidayPlanService;

/**
 * 假日计划Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
@Service
public class AcsHolidayPlanServiceImpl implements IAcsHolidayPlanService 
{
    @Autowired
    private AcsHolidayPlanMapper acsHolidayPlanMapper;

    /**
     * 查询假日计划
     * 
     * @param planId 假日计划ID
     * @return 假日计划
     */
    @Override
    public AcsHolidayPlan selectAcsHolidayPlanById(Long planId)
    {
        return acsHolidayPlanMapper.selectAcsHolidayPlanById(planId);
    }

    /**
     * 查询假日计划列表
     * 
     * @param acsHolidayPlan 假日计划
     * @return 假日计划
     */
    @Override
    public List<AcsHolidayPlan> selectAcsHolidayPlanList(AcsHolidayPlan acsHolidayPlan)
    {
        return acsHolidayPlanMapper.selectAcsHolidayPlanList(acsHolidayPlan);
    }

    /**
     * 新增假日计划
     * 
     * @param acsHolidayPlan 假日计划
     * @return 结果
     */
    @Override
    public int insertAcsHolidayPlan(AcsHolidayPlan acsHolidayPlan)
    {
        acsHolidayPlan.setCreateTime(DateUtils.getNowDate());
        return acsHolidayPlanMapper.insertAcsHolidayPlan(acsHolidayPlan);
    }

    /**
     * 修改假日计划
     * 
     * @param acsHolidayPlan 假日计划
     * @return 结果
     */
    @Override
    public int updateAcsHolidayPlan(AcsHolidayPlan acsHolidayPlan)
    {
        acsHolidayPlan.setUpdateTime(DateUtils.getNowDate());
        return acsHolidayPlanMapper.updateAcsHolidayPlan(acsHolidayPlan);
    }

    /**
     * 批量删除假日计划
     * 
     * @param planIds 需要删除的假日计划ID
     * @return 结果
     */
    @Override
    public int deleteAcsHolidayPlanByIds(Long[] planIds)
    {
        return acsHolidayPlanMapper.deleteAcsHolidayPlanByIds(planIds);
    }

    /**
     * 删除假日计划信息
     * 
     * @param planId 假日计划ID
     * @return 结果
     */
    @Override
    public int deleteAcsHolidayPlanById(Long planId)
    {
        return acsHolidayPlanMapper.deleteAcsHolidayPlanById(planId);
    }

    @Override
    public List<Long> selectPlanListByGroupId(Long groupId) {
        return acsHolidayPlanMapper.selectPlanListByGroupId(groupId);
    }
}
