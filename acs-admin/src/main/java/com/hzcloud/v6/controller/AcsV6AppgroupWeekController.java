package com.hzcloud.v6.controller;

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
import com.hzcloud.v6.domain.AcsV6AppgroupWeek;
import com.hzcloud.v6.service.IAcsV6AppgroupWeekService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 周计划模板Controller
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
@RestController
@RequestMapping("/appgroup/week")
public class AcsV6AppgroupWeekController extends BaseController
{
    @Autowired
    private IAcsV6AppgroupWeekService acsV6AppgroupWeekService;

    /**
     * 查询周计划模板列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:week:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsV6AppgroupWeek acsV6AppgroupWeek)
    {
        startPage();
        List<AcsV6AppgroupWeek> list = acsV6AppgroupWeekService.selectAcsV6AppgroupWeekList(acsV6AppgroupWeek);
        return getDataTable(list);
    }

    /**
     * 导出周计划模板列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:week:export')")
    @Log(title = "周计划模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsV6AppgroupWeek acsV6AppgroupWeek)
    {
        List<AcsV6AppgroupWeek> list = acsV6AppgroupWeekService.selectAcsV6AppgroupWeekList(acsV6AppgroupWeek);
        ExcelUtil<AcsV6AppgroupWeek> util = new ExcelUtil<AcsV6AppgroupWeek>(AcsV6AppgroupWeek.class);
        return util.exportExcel(list, "周计划模板数据");
    }

    /**
     * 获取周计划模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('appgroup:week:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(acsV6AppgroupWeekService.selectAcsV6AppgroupWeekById(id));
    }

    /**
     * 新增周计划模板
     */
    @PreAuthorize("@ss.hasPermi('appgroup:week:add')")
    @Log(title = "周计划模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsV6AppgroupWeek acsV6AppgroupWeek)
    {
        return toAjax(acsV6AppgroupWeekService.insertAcsV6AppgroupWeek(acsV6AppgroupWeek));
    }

    /**
     * 修改周计划模板
     */
    @PreAuthorize("@ss.hasPermi('appgroup:week:edit')")
    @Log(title = "周计划模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsV6AppgroupWeek acsV6AppgroupWeek)
    {
        return toAjax(acsV6AppgroupWeekService.updateAcsV6AppgroupWeek(acsV6AppgroupWeek));
    }

    /**
     * 删除周计划模板
     */
    @PreAuthorize("@ss.hasPermi('appgroup:week:remove')")
    @Log(title = "周计划模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(acsV6AppgroupWeekService.deleteAcsV6AppgroupWeekByIds(ids));
    }
}
