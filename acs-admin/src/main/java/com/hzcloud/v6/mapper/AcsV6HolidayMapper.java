package com.hzcloud.v6.mapper;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6Holiday;

/**
 * 假日设置Mapper接口
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
public interface AcsV6HolidayMapper 
{
    /**
     * 查询假日设置
     * 
     * @param month 假日设置ID
     * @return 假日设置
     */
    public AcsV6Holiday selectAcsV6HolidayById(String month);

    /**
     * 查询假日设置列表
     * 
     * @param acsV6Holiday 假日设置
     * @return 假日设置集合
     */
    public List<AcsV6Holiday> selectAcsV6HolidayList(AcsV6Holiday acsV6Holiday);

    /**
     * 新增假日设置
     * 
     * @param acsV6Holiday 假日设置
     * @return 结果
     */
    public int insertAcsV6Holiday(AcsV6Holiday acsV6Holiday);

    /**
     * 修改假日设置
     * 
     * @param acsV6Holiday 假日设置
     * @return 结果
     */
    public int updateAcsV6Holiday(AcsV6Holiday acsV6Holiday);

    /**
     * 删除假日设置
     * 
     * @param month 假日设置ID
     * @return 结果
     */
    public int deleteAcsV6HolidayById(String month);

    /**
     * 批量删除假日设置
     * 
     * @param months 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsV6HolidayByIds(String[] months);
}
