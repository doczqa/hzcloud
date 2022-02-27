package com.hzcloud.v6.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.v6.mapper.AcsV6HolidayMapper;
import com.hzcloud.v6.domain.AcsV6Holiday;
import com.hzcloud.v6.service.IAcsV6HolidayService;

/**
 * 假日设置Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
@Service
public class AcsV6HolidayServiceImpl implements IAcsV6HolidayService 
{
    @Autowired
    private AcsV6HolidayMapper acsV6HolidayMapper;

    /**
     * 查询假日设置
     * 
     * @param month 假日设置ID
     * @return 假日设置
     */
    @Override
    public AcsV6Holiday selectAcsV6HolidayById(String month)
    {
        return acsV6HolidayMapper.selectAcsV6HolidayById(month);
    }

    /**
     * 查询假日设置列表
     * 
     * @param acsV6Holiday 假日设置
     * @return 假日设置
     */
    @Override
    public List<AcsV6Holiday> selectAcsV6HolidayList(AcsV6Holiday acsV6Holiday)
    {
        return acsV6HolidayMapper.selectAcsV6HolidayList(acsV6Holiday);
    }

    /**
     * 新增假日设置
     * 
     * @param acsV6Holiday 假日设置
     * @return 结果
     */
    @Override
    public int insertAcsV6Holiday(AcsV6Holiday acsV6Holiday)
    {
        return acsV6HolidayMapper.insertAcsV6Holiday(acsV6Holiday);
    }

    /**
     * 修改假日设置
     * 
     * @param acsV6Holiday 假日设置
     * @return 结果
     */
    @Override
    public int updateAcsV6Holiday(AcsV6Holiday acsV6Holiday)
    {
        return acsV6HolidayMapper.updateAcsV6Holiday(acsV6Holiday);
    }

    /**
     * 批量删除假日设置
     * 
     * @param months 需要删除的假日设置ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6HolidayByIds(String[] months)
    {
        return acsV6HolidayMapper.deleteAcsV6HolidayByIds(months);
    }

    /**
     * 删除假日设置信息
     * 
     * @param month 假日设置ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6HolidayById(String month)
    {
        return acsV6HolidayMapper.deleteAcsV6HolidayById(month);
    }
}
