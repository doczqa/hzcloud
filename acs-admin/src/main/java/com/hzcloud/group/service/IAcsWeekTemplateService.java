package com.hzcloud.group.service;

import java.util.List;
import com.hzcloud.group.domain.AcsWeekTemplate;

/**
 * 周计划模板Service接口
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
public interface IAcsWeekTemplateService 
{
    /**
     * 查询周计划模板
     * 
     * @param templateId 周计划模板ID
     * @return 周计划模板
     */
    public AcsWeekTemplate selectAcsWeekTemplateById(Long templateId);

    /**
     * 查询周计划模板列表
     * 
     * @param acsWeekTemplate 周计划模板
     * @return 周计划模板集合
     */
    public List<AcsWeekTemplate> selectAcsWeekTemplateList(AcsWeekTemplate acsWeekTemplate);

    /**
     * 新增周计划模板
     * 
     * @param acsWeekTemplate 周计划模板
     * @return 结果
     */
    public int insertAcsWeekTemplate(AcsWeekTemplate acsWeekTemplate);

    /**
     * 修改周计划模板
     * 
     * @param acsWeekTemplate 周计划模板
     * @return 结果
     */
    public int updateAcsWeekTemplate(AcsWeekTemplate acsWeekTemplate);

    /**
     * 批量删除周计划模板
     * 
     * @param templateIds 需要删除的周计划模板ID
     * @return 结果
     */
    public int deleteAcsWeekTemplateByIds(Long[] templateIds);

    /**
     * 删除周计划模板信息
     * 
     * @param templateId 周计划模板ID
     * @return 结果
     */
    public int deleteAcsWeekTemplateById(Long templateId);
}
