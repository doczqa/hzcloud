package com.hzcloud.group.service.impl;

import java.util.List;
import com.hzcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.group.mapper.AcsWeekTemplateMapper;
import com.hzcloud.group.domain.AcsWeekTemplate;
import com.hzcloud.group.service.IAcsWeekTemplateService;

/**
 * 周计划模板Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
@Service
public class AcsWeekTemplateServiceImpl implements IAcsWeekTemplateService 
{
    @Autowired
    private AcsWeekTemplateMapper acsWeekTemplateMapper;

    /**
     * 查询周计划模板
     * 
     * @param templateId 周计划模板ID
     * @return 周计划模板
     */
    @Override
    public AcsWeekTemplate selectAcsWeekTemplateById(Long templateId)
    {
        return acsWeekTemplateMapper.selectAcsWeekTemplateById(templateId);
    }

    /**
     * 查询周计划模板列表
     * 
     * @param acsWeekTemplate 周计划模板
     * @return 周计划模板
     */
    @Override
    public List<AcsWeekTemplate> selectAcsWeekTemplateList(AcsWeekTemplate acsWeekTemplate)
    {
        return acsWeekTemplateMapper.selectAcsWeekTemplateList(acsWeekTemplate);
    }

    /**
     * 新增周计划模板
     * 
     * @param acsWeekTemplate 周计划模板
     * @return 结果
     */
    @Override
    public int insertAcsWeekTemplate(AcsWeekTemplate acsWeekTemplate)
    {
        acsWeekTemplate.setCreateTime(DateUtils.getNowDate());
        return acsWeekTemplateMapper.insertAcsWeekTemplate(acsWeekTemplate);
    }

    /**
     * 修改周计划模板
     * 
     * @param acsWeekTemplate 周计划模板
     * @return 结果
     */
    @Override
    public int updateAcsWeekTemplate(AcsWeekTemplate acsWeekTemplate)
    {
        acsWeekTemplate.setUpdateTime(DateUtils.getNowDate());
        return acsWeekTemplateMapper.updateAcsWeekTemplate(acsWeekTemplate);
    }

    /**
     * 批量删除周计划模板
     * 
     * @param templateIds 需要删除的周计划模板ID
     * @return 结果
     */
    @Override
    public int deleteAcsWeekTemplateByIds(Long[] templateIds)
    {
        return acsWeekTemplateMapper.deleteAcsWeekTemplateByIds(templateIds);
    }

    /**
     * 删除周计划模板信息
     * 
     * @param templateId 周计划模板ID
     * @return 结果
     */
    @Override
    public int deleteAcsWeekTemplateById(Long templateId)
    {
        return acsWeekTemplateMapper.deleteAcsWeekTemplateById(templateId);
    }
}
