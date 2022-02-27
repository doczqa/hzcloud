package com.hzcloud.group.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.group.domain.AcsWeekTemplate;
import com.hzcloud.group.service.IAcsWeekTemplateService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 周计划模板Controller
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
@RestController
@RequestMapping("/week/template")
public class AcsWeekTemplateController extends BaseController
{
    @Autowired
    private IAcsWeekTemplateService acsWeekTemplateService;

    /**
     * 查询周计划模板列表
     */
    @PreAuthorize("@ss.hasPermi('week:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsWeekTemplate acsWeekTemplate)
    {
        startPage();
        List<AcsWeekTemplate> list = acsWeekTemplateService.selectAcsWeekTemplateList(acsWeekTemplate);
        return getDataTable(list);
    }

    /**
     * 导出周计划模板列表
     */
    @PreAuthorize("@ss.hasPermi('week:template:export')")
    @Log(title = "周计划模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsWeekTemplate acsWeekTemplate)
    {
        List<AcsWeekTemplate> list = acsWeekTemplateService.selectAcsWeekTemplateList(acsWeekTemplate);
        ExcelUtil<AcsWeekTemplate> util = new ExcelUtil<AcsWeekTemplate>(AcsWeekTemplate.class);
        return util.exportExcel(list, "周计划模板数据");
    }

    /**
     * 获取周计划模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('week:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return AjaxResult.success(acsWeekTemplateService.selectAcsWeekTemplateById(templateId));
    }

    /**
     * 新增周计划模板
     */
    @PreAuthorize("@ss.hasPermi('week:template:add')")
    @Log(title = "周计划模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsWeekTemplate acsWeekTemplate)
    {
        return toAjax(acsWeekTemplateService.insertAcsWeekTemplate(acsWeekTemplate));
    }

    /**
     * 修改周计划模板
     */
    @PreAuthorize("@ss.hasPermi('week:template:edit')")
    @Log(title = "周计划模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsWeekTemplate acsWeekTemplate)
    {
        return toAjax(acsWeekTemplateService.updateAcsWeekTemplate(acsWeekTemplate));
    }

    /**
     * 删除周计划模板
     */
    @PreAuthorize("@ss.hasPermi('week:template:remove')")
    @Log(title = "周计划模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(acsWeekTemplateService.deleteAcsWeekTemplateByIds(templateIds));
    }
}
