package com.hzcloud.v6.mapper;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6Timezone;

/**
 * 时区设置Mapper接口
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
public interface AcsV6TimezoneMapper 
{
    /**
     * 查询时区设置
     * 
     * @param id 时区设置ID
     * @return 时区设置
     */
    public AcsV6Timezone selectAcsV6TimezoneById(String id);

    /**
     * 查询时区设置列表
     * 
     * @param acsV6Timezone 时区设置
     * @return 时区设置集合
     */
    public List<AcsV6Timezone> selectAcsV6TimezoneList(AcsV6Timezone acsV6Timezone);

    /**
     * 新增时区设置
     * 
     * @param acsV6Timezone 时区设置
     * @return 结果
     */
    public int insertAcsV6Timezone(AcsV6Timezone acsV6Timezone);

    /**
     * 修改时区设置
     * 
     * @param acsV6Timezone 时区设置
     * @return 结果
     */
    public int updateAcsV6Timezone(AcsV6Timezone acsV6Timezone);

    /**
     * 删除时区设置
     * 
     * @param id 时区设置ID
     * @return 结果
     */
    public int deleteAcsV6TimezoneById(String id);

    /**
     * 批量删除时区设置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsV6TimezoneByIds(String[] ids);
}
