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
import com.hzcloud.group.domain.AcsHolidayPlan;
import com.hzcloud.group.service.IAcsHolidayPlanService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 假日计划Controller
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
@RestController
@RequestMapping("/holiday/plan")
public class AcsHolidayPlanController extends BaseController
{
    @Autowired
    private IAcsHolidayPlanService acsHolidayPlanService;

    /**
     * 查询假日计划列表
     */
    @PreAuthorize("@ss.hasPermi('holiday:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsHolidayPlan acsHolidayPlan)
    {
        startPage();
        List<AcsHolidayPlan> list = acsHolidayPlanService.selectAcsHolidayPlanList(acsHolidayPlan);
        return getDataTable(list);
    }

    /**
     * 导出假日计划列表
     */
    @PreAuthorize("@ss.hasPermi('holiday:plan:export')")
    @Log(title = "假日计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsHolidayPlan acsHolidayPlan)
    {
        List<AcsHolidayPlan> list = acsHolidayPlanService.selectAcsHolidayPlanList(acsHolidayPlan);
        ExcelUtil<AcsHolidayPlan> util = new ExcelUtil<AcsHolidayPlan>(AcsHolidayPlan.class);
        return util.exportExcel(list, "假日计划数据");
    }

    /**
     * 获取假日计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('holiday:plan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return AjaxResult.success(acsHolidayPlanService.selectAcsHolidayPlanById(planId));
    }

    /**
     * 新增假日计划
     */
    @PreAuthorize("@ss.hasPermi('holiday:plan:add')")
    @Log(title = "假日计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsHolidayPlan acsHolidayPlan)
    {
        return toAjax(acsHolidayPlanService.insertAcsHolidayPlan(acsHolidayPlan));
    }

    /**
     * 修改假日计划
     */
    @PreAuthorize("@ss.hasPermi('holiday:plan:edit')")
    @Log(title = "假日计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsHolidayPlan acsHolidayPlan)
    {
        return toAjax(acsHolidayPlanService.updateAcsHolidayPlan(acsHolidayPlan));
    }

    /**
     * 删除假日计划
     */
    @PreAuthorize("@ss.hasPermi('holiday:plan:remove')")
    @Log(title = "假日计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(acsHolidayPlanService.deleteAcsHolidayPlanByIds(planIds));
    }
}
