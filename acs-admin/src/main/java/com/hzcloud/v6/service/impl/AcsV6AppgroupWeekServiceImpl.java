package com.hzcloud.v6.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.v6.mapper.AcsV6AppgroupWeekMapper;
import com.hzcloud.v6.domain.AcsV6AppgroupWeek;
import com.hzcloud.v6.service.IAcsV6AppgroupWeekService;

/**
 * 周计划模板Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
@Service
public class AcsV6AppgroupWeekServiceImpl implements IAcsV6AppgroupWeekService 
{
    @Autowired
    private AcsV6AppgroupWeekMapper acsV6AppgroupWeekMapper;

    /**
     * 查询周计划模板
     * 
     * @param id 周计划模板ID
     * @return 周计划模板
     */
    @Override
    public AcsV6AppgroupWeek selectAcsV6AppgroupWeekById(String id)
    {
        return acsV6AppgroupWeekMapper.selectAcsV6AppgroupWeekById(id);
    }

    /**
     * 查询周计划模板列表
     * 
     * @param acsV6AppgroupWeek 周计划模板
     * @return 周计划模板
     */
    @Override
    public List<AcsV6AppgroupWeek> selectAcsV6AppgroupWeekList(AcsV6AppgroupWeek acsV6AppgroupWeek)
    {
        return acsV6AppgroupWeekMapper.selectAcsV6AppgroupWeekList(acsV6AppgroupWeek);
    }

    /**
     * 新增周计划模板
     * 
     * @param acsV6AppgroupWeek 周计划模板
     * @return 结果
     */
    @Override
    public int insertAcsV6AppgroupWeek(AcsV6AppgroupWeek acsV6AppgroupWeek)
    {
        return acsV6AppgroupWeekMapper.insertAcsV6AppgroupWeek(acsV6AppgroupWeek);
    }

    /**
     * 修改周计划模板
     * 
     * @param acsV6AppgroupWeek 周计划模板
     * @return 结果
     */
    @Override
    public int updateAcsV6AppgroupWeek(AcsV6AppgroupWeek acsV6AppgroupWeek)
    {
        return acsV6AppgroupWeekMapper.updateAcsV6AppgroupWeek(acsV6AppgroupWeek);
    }

    /**
     * 批量删除周计划模板
     * 
     * @param ids 需要删除的周计划模板ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6AppgroupWeekByIds(String[] ids)
    {
        return acsV6AppgroupWeekMapper.deleteAcsV6AppgroupWeekByIds(ids);
    }

    /**
     * 删除周计划模板信息
     * 
     * @param id 周计划模板ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6AppgroupWeekById(String id)
    {
        return acsV6AppgroupWeekMapper.deleteAcsV6AppgroupWeekById(id);
    }
}
