package com.hzcloud.group.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.hzcloud.common.utils.DateUtils;
import com.hzcloud.common.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.group.mapper.AcsHolidayGroupMapper;
import com.hzcloud.group.mapper.AcsHolidayGroupPlanMapper;
import com.hzcloud.group.domain.AcsHolidayGroup;
import com.hzcloud.group.domain.AcsHolidayGroupPlan;
import com.hzcloud.group.service.IAcsHolidayGroupService;

/**
 * 假日组Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
@Service
public class AcsHolidayGroupServiceImpl implements IAcsHolidayGroupService 
{
    @Autowired
    private AcsHolidayGroupMapper acsHolidayGroupMapper;

    @Autowired
    private AcsHolidayGroupPlanMapper acsHolidayGroupPlanMapper;

    /**
     * 查询假日组
     * 
     * @param groupId 假日组ID
     * @return 假日组
     */
    @Override
    public AcsHolidayGroup selectAcsHolidayGroupById(Long groupId)
    {
        return acsHolidayGroupMapper.selectAcsHolidayGroupById(groupId);
    }

    /**
     * 查询假日组列表
     * 
     * @param acsHolidayGroup 假日组
     * @return 假日组
     */
    @Override
    public List<AcsHolidayGroup> selectAcsHolidayGroupList(AcsHolidayGroup acsHolidayGroup)
    {
        return acsHolidayGroupMapper.selectAcsHolidayGroupList(acsHolidayGroup);
    }

    /**
     * 新增假日组
     * 
     * @param acsHolidayGroup 假日组
     * @return 结果
     */
    @Override
    public int insertAcsHolidayGroup(AcsHolidayGroup acsHolidayGroup)
    {
        acsHolidayGroup.setCreateTime(DateUtils.getNowDate());
        int rows = acsHolidayGroupMapper.insertAcsHolidayGroup(acsHolidayGroup);
        // 新增区域与控制器管理
        insertGroupPlan(acsHolidayGroup);
        return rows;
    }

    /**
     * 修改假日组
     * 
     * @param acsHolidayGroup 假日组
     * @return 结果
     */
    @Override
    public int updateAcsHolidayGroup(AcsHolidayGroup acsHolidayGroup)
    {
        Long groupId = acsHolidayGroup.getGroupId();
        acsHolidayGroup.setUpdateTime(DateUtils.getNowDate());
        // 删除区域与控制器关联
        acsHolidayGroupPlanMapper.deleteGroupPlanByGroupId(groupId);
        // 新增区域与控制器管理
        insertGroupPlan(acsHolidayGroup);
        return acsHolidayGroupMapper.updateAcsHolidayGroup(acsHolidayGroup);
    }

    /**
     * 批量删除假日组
     * 
     * @param groupIds 需要删除的假日组ID
     * @return 结果
     */
    @Override
    public int deleteAcsHolidayGroupByIds(Long[] groupIds)
    {
        acsHolidayGroupPlanMapper.deleteGroupPlan(groupIds);
        return acsHolidayGroupMapper.deleteAcsHolidayGroupByIds(groupIds);
    }

    /**
     * 删除假日组信息
     * 
     * @param groupId 假日组ID
     * @return 结果
     */
    @Override
    public int deleteAcsHolidayGroupById(Long groupId)
    {
        acsHolidayGroupPlanMapper.deleteGroupPlanByGroupId(groupId);
        return acsHolidayGroupMapper.deleteAcsHolidayGroupById(groupId);
    }

    /**
     * 新增假日组假日计划信息
     * 
     * @param group 假日组对象
     */
    public void insertGroupPlan(AcsHolidayGroup group)
    {
        Long[] plans = group.getPlanIds();
        if (StringUtils.isNotNull(plans))
        {
            // 新增用户与角色管理
            List<AcsHolidayGroupPlan> list = new ArrayList<AcsHolidayGroupPlan>();
            for (Long planId : plans)
            {
                AcsHolidayGroupPlan gp = new AcsHolidayGroupPlan();
                gp.setGroupId(group.getGroupId());
                gp.setPlanId(planId);
                list.add(gp);
            }
            if (list.size() > 0)
            {
                acsHolidayGroupPlanMapper.batchGroupPlan(list);
            }
        }
    }
}
