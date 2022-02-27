package com.hzcloud.group.service;

import java.util.List;
import com.hzcloud.group.domain.AcsHolidayGroup;

/**
 * 假日组Service接口
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
public interface IAcsHolidayGroupService 
{
    /**
     * 查询假日组
     * 
     * @param groupId 假日组ID
     * @return 假日组
     */
    public AcsHolidayGroup selectAcsHolidayGroupById(Long groupId);

    /**
     * 查询假日组列表
     * 
     * @param acsHolidayGroup 假日组
     * @return 假日组集合
     */
    public List<AcsHolidayGroup> selectAcsHolidayGroupList(AcsHolidayGroup acsHolidayGroup);

    /**
     * 新增假日组
     * 
     * @param acsHolidayGroup 假日组
     * @return 结果
     */
    public int insertAcsHolidayGroup(AcsHolidayGroup acsHolidayGroup);

    /**
     * 修改假日组
     * 
     * @param acsHolidayGroup 假日组
     * @return 结果
     */
    public int updateAcsHolidayGroup(AcsHolidayGroup acsHolidayGroup);

    /**
     * 批量删除假日组
     * 
     * @param groupIds 需要删除的假日组ID
     * @return 结果
     */
    public int deleteAcsHolidayGroupByIds(Long[] groupIds);

    /**
     * 删除假日组信息
     * 
     * @param groupId 假日组ID
     * @return 结果
     */
    public int deleteAcsHolidayGroupById(Long groupId);

}
