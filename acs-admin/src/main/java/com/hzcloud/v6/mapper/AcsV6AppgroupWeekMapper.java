package com.hzcloud.v6.mapper;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6AppgroupWeek;

/**
 * 周计划模板Mapper接口
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
public interface AcsV6AppgroupWeekMapper 
{
    /**
     * 查询周计划模板
     * 
     * @param id 周计划模板ID
     * @return 周计划模板
     */
    public AcsV6AppgroupWeek selectAcsV6AppgroupWeekById(String id);

    /**
     * 查询周计划模板列表
     * 
     * @param acsV6AppgroupWeek 周计划模板
     * @return 周计划模板集合
     */
    public List<AcsV6AppgroupWeek> selectAcsV6AppgroupWeekList(AcsV6AppgroupWeek acsV6AppgroupWeek);

    /**
     * 新增周计划模板
     * 
     * @param acsV6AppgroupWeek 周计划模板
     * @return 结果
     */
    public int insertAcsV6AppgroupWeek(AcsV6AppgroupWeek acsV6AppgroupWeek);

    /**
     * 修改周计划模板
     * 
     * @param acsV6AppgroupWeek 周计划模板
     * @return 结果
     */
    public int updateAcsV6AppgroupWeek(AcsV6AppgroupWeek acsV6AppgroupWeek);

    /**
     * 删除周计划模板
     * 
     * @param id 周计划模板ID
     * @return 结果
     */
    public int deleteAcsV6AppgroupWeekById(String id);

    /**
     * 批量删除周计划模板
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsV6AppgroupWeekByIds(String[] ids);
}
