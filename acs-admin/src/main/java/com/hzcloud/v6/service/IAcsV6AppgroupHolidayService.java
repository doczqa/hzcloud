package com.hzcloud.v6.service;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6AppgroupHoliday;

/**
 * 假日计划Service接口
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
public interface IAcsV6AppgroupHolidayService 
{
    /**
     * 查询假日计划
     * 
     * @param id 假日计划ID
     * @return 假日计划
     */
    public AcsV6AppgroupHoliday selectAcsV6AppgroupHolidayById(String id);

    /**
     * 查询假日计划列表
     * 
     * @param acsV6AppgroupHoliday 假日计划
     * @return 假日计划集合
     */
    public List<AcsV6AppgroupHoliday> selectAcsV6AppgroupHolidayList(AcsV6AppgroupHoliday acsV6AppgroupHoliday);

    /**
     * 新增假日计划
     * 
     * @param acsV6AppgroupHoliday 假日计划
     * @return 结果
     */
    public int insertAcsV6AppgroupHoliday(AcsV6AppgroupHoliday acsV6AppgroupHoliday);

    /**
     * 修改假日计划
     * 
     * @param acsV6AppgroupHoliday 假日计划
     * @return 结果
     */
    public int updateAcsV6AppgroupHoliday(AcsV6AppgroupHoliday acsV6AppgroupHoliday);

    /**
     * 批量删除假日计划
     * 
     * @param ids 需要删除的假日计划ID
     * @return 结果
     */
    public int deleteAcsV6AppgroupHolidayByIds(String[] ids);

    /**
     * 删除假日计划信息
     * 
     * @param id 假日计划ID
     * @return 结果
     */
    public int deleteAcsV6AppgroupHolidayById(String id);
}
