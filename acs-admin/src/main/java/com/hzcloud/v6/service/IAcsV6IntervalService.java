package com.hzcloud.v6.service;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6Interval;

/**
 * 时段设置Service接口
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
public interface IAcsV6IntervalService 
{
    /**
     * 查询时段设置
     * 
     * @param id 时段设置ID
     * @return 时段设置
     */
    public AcsV6Interval selectAcsV6IntervalById(String id);

    /**
     * 查询时段设置列表
     * 
     * @param acsV6Interval 时段设置
     * @return 时段设置集合
     */
    public List<AcsV6Interval> selectAcsV6IntervalList(AcsV6Interval acsV6Interval);

    /**
     * 新增时段设置
     * 
     * @param acsV6Interval 时段设置
     * @return 结果
     */
    public int insertAcsV6Interval(AcsV6Interval acsV6Interval);

    /**
     * 修改时段设置
     * 
     * @param acsV6Interval 时段设置
     * @return 结果
     */
    public int updateAcsV6Interval(AcsV6Interval acsV6Interval);

    /**
     * 批量删除时段设置
     * 
     * @param ids 需要删除的时段设置ID
     * @return 结果
     */
    public int deleteAcsV6IntervalByIds(String[] ids);

    /**
     * 删除时段设置信息
     * 
     * @param id 时段设置ID
     * @return 结果
     */
    public int deleteAcsV6IntervalById(String id);
}
