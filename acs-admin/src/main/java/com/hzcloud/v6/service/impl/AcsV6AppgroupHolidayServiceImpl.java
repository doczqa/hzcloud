package com.hzcloud.v6.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.v6.mapper.AcsV6AppgroupHolidayMapper;
import com.hzcloud.v6.domain.AcsV6AppgroupHoliday;
import com.hzcloud.v6.service.IAcsV6AppgroupHolidayService;

/**
 * 假日计划Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
@Service
public class AcsV6AppgroupHolidayServiceImpl implements IAcsV6AppgroupHolidayService 
{
    @Autowired
    private AcsV6AppgroupHolidayMapper acsV6AppgroupHolidayMapper;

    /**
     * 查询假日计划
     * 
     * @param id 假日计划ID
     * @return 假日计划
     */
    @Override
    public AcsV6AppgroupHoliday selectAcsV6AppgroupHolidayById(String id)
    {
        return acsV6AppgroupHolidayMapper.selectAcsV6AppgroupHolidayById(id);
    }

    /**
     * 查询假日计划列表
     * 
     * @param acsV6AppgroupHoliday 假日计划
     * @return 假日计划
     */
    @Override
    public List<AcsV6AppgroupHoliday> selectAcsV6AppgroupHolidayList(AcsV6AppgroupHoliday acsV6AppgroupHoliday)
    {
        return acsV6AppgroupHolidayMapper.selectAcsV6AppgroupHolidayList(acsV6AppgroupHoliday);
    }

    /**
     * 新增假日计划
     * 
     * @param acsV6AppgroupHoliday 假日计划
     * @return 结果
     */
    @Override
    public int insertAcsV6AppgroupHoliday(AcsV6AppgroupHoliday acsV6AppgroupHoliday)
    {
        return acsV6AppgroupHolidayMapper.insertAcsV6AppgroupHoliday(acsV6AppgroupHoliday);
    }

    /**
     * 修改假日计划
     * 
     * @param acsV6AppgroupHoliday 假日计划
     * @return 结果
     */
    @Override
    public int updateAcsV6AppgroupHoliday(AcsV6AppgroupHoliday acsV6AppgroupHoliday)
    {
        return acsV6AppgroupHolidayMapper.updateAcsV6AppgroupHoliday(acsV6AppgroupHoliday);
    }

    /**
     * 批量删除假日计划
     * 
     * @param ids 需要删除的假日计划ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6AppgroupHolidayByIds(String[] ids)
    {
        return acsV6AppgroupHolidayMapper.deleteAcsV6AppgroupHolidayByIds(ids);
    }

    /**
     * 删除假日计划信息
     * 
     * @param id 假日计划ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6AppgroupHolidayById(String id)
    {
        return acsV6AppgroupHolidayMapper.deleteAcsV6AppgroupHolidayById(id);
    }
}
