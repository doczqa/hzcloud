package com.hzcloud.v6.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.v6.mapper.AcsV6IntervalMapper;
import com.hzcloud.v6.domain.AcsV6Interval;
import com.hzcloud.v6.service.IAcsV6IntervalService;

/**
 * 时段设置Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
@Service
public class AcsV6IntervalServiceImpl implements IAcsV6IntervalService 
{
    @Autowired
    private AcsV6IntervalMapper acsV6IntervalMapper;

    /**
     * 查询时段设置
     * 
     * @param id 时段设置ID
     * @return 时段设置
     */
    @Override
    public AcsV6Interval selectAcsV6IntervalById(String id)
    {
        return acsV6IntervalMapper.selectAcsV6IntervalById(id);
    }

    /**
     * 查询时段设置列表
     * 
     * @param acsV6Interval 时段设置
     * @return 时段设置
     */
    @Override
    public List<AcsV6Interval> selectAcsV6IntervalList(AcsV6Interval acsV6Interval)
    {
        return acsV6IntervalMapper.selectAcsV6IntervalList(acsV6Interval);
    }

    /**
     * 新增时段设置
     * 
     * @param acsV6Interval 时段设置
     * @return 结果
     */
    @Override
    public int insertAcsV6Interval(AcsV6Interval acsV6Interval)
    {
        return acsV6IntervalMapper.insertAcsV6Interval(acsV6Interval);
    }

    /**
     * 修改时段设置
     * 
     * @param acsV6Interval 时段设置
     * @return 结果
     */
    @Override
    public int updateAcsV6Interval(AcsV6Interval acsV6Interval)
    {
        return acsV6IntervalMapper.updateAcsV6Interval(acsV6Interval);
    }

    /**
     * 批量删除时段设置
     * 
     * @param ids 需要删除的时段设置ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6IntervalByIds(String[] ids)
    {
        return acsV6IntervalMapper.deleteAcsV6IntervalByIds(ids);
    }

    /**
     * 删除时段设置信息
     * 
     * @param id 时段设置ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6IntervalById(String id)
    {
        return acsV6IntervalMapper.deleteAcsV6IntervalById(id);
    }
}
