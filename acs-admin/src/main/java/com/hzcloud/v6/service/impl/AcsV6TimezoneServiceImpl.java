package com.hzcloud.v6.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.v6.mapper.AcsV6TimezoneMapper;
import com.hzcloud.v6.domain.AcsV6Timezone;
import com.hzcloud.v6.service.IAcsV6TimezoneService;

/**
 * 时区设置Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
@Service
public class AcsV6TimezoneServiceImpl implements IAcsV6TimezoneService 
{
    @Autowired
    private AcsV6TimezoneMapper acsV6TimezoneMapper;

    /**
     * 查询时区设置
     * 
     * @param id 时区设置ID
     * @return 时区设置
     */
    @Override
    public AcsV6Timezone selectAcsV6TimezoneById(String id)
    {
        return acsV6TimezoneMapper.selectAcsV6TimezoneById(id);
    }

    /**
     * 查询时区设置列表
     * 
     * @param acsV6Timezone 时区设置
     * @return 时区设置
     */
    @Override
    public List<AcsV6Timezone> selectAcsV6TimezoneList(AcsV6Timezone acsV6Timezone)
    {
        return acsV6TimezoneMapper.selectAcsV6TimezoneList(acsV6Timezone);
    }

    /**
     * 新增时区设置
     * 
     * @param acsV6Timezone 时区设置
     * @return 结果
     */
    @Override
    public int insertAcsV6Timezone(AcsV6Timezone acsV6Timezone)
    {
        return acsV6TimezoneMapper.insertAcsV6Timezone(acsV6Timezone);
    }

    /**
     * 修改时区设置
     * 
     * @param acsV6Timezone 时区设置
     * @return 结果
     */
    @Override
    public int updateAcsV6Timezone(AcsV6Timezone acsV6Timezone)
    {
        return acsV6TimezoneMapper.updateAcsV6Timezone(acsV6Timezone);
    }

    /**
     * 批量删除时区设置
     * 
     * @param ids 需要删除的时区设置ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6TimezoneByIds(String[] ids)
    {
        return acsV6TimezoneMapper.deleteAcsV6TimezoneByIds(ids);
    }

    /**
     * 删除时区设置信息
     * 
     * @param id 时区设置ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6TimezoneById(String id)
    {
        return acsV6TimezoneMapper.deleteAcsV6TimezoneById(id);
    }
}
