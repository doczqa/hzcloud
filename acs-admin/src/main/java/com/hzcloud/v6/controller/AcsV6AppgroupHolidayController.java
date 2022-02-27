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
import com.hzcloud.v6.domain.AcsV6AppgroupHoliday;
import com.hzcloud.v6.service.IAcsV6AppgroupHolidayService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 假日计划Controller
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
@RestController
@RequestMapping("/appgroup/holidayplan")
public class AcsV6AppgroupHolidayController extends BaseController
{
    @Autowired
    private IAcsV6AppgroupHolidayService acsV6AppgroupHolidayService;

    /**
     * 查询假日计划列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holidayplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsV6AppgroupHoliday acsV6AppgroupHoliday)
    {
        startPage();
        List<AcsV6AppgroupHoliday> list = acsV6AppgroupHolidayService.selectAcsV6AppgroupHolidayList(acsV6AppgroupHoliday);
        return getDataTable(list);
    }

    /**
     * 导出假日计划列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holidayplan:export')")
    @Log(title = "假日计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsV6AppgroupHoliday acsV6AppgroupHoliday)
    {
        List<AcsV6AppgroupHoliday> list = acsV6AppgroupHolidayService.selectAcsV6AppgroupHolidayList(acsV6AppgroupHoliday);
        ExcelUtil<AcsV6AppgroupHoliday> util = new ExcelUtil<AcsV6AppgroupHoliday>(AcsV6AppgroupHoliday.class);
        return util.exportExcel(list, "假日计划数据");
    }

    /**
     * 获取假日计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holidayplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(acsV6AppgroupHolidayService.selectAcsV6AppgroupHolidayById(id));
    }

    /**
     * 新增假日计划
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holidayplan:add')")
    @Log(title = "假日计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsV6AppgroupHoliday acsV6AppgroupHoliday)
    {
        return toAjax(acsV6AppgroupHolidayService.insertAcsV6AppgroupHoliday(acsV6AppgroupHoliday));
    }

    /**
     * 修改假日计划
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holidayplan:edit')")
    @Log(title = "假日计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsV6AppgroupHoliday acsV6AppgroupHoliday)
    {
        return toAjax(acsV6AppgroupHolidayService.updateAcsV6AppgroupHoliday(acsV6AppgroupHoliday));
    }

    /**
     * 删除假日计划
     */
    @PreAuthorize("@ss.hasPermi('appgroup:holidayplan:remove')")
    @Log(title = "假日计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(acsV6AppgroupHolidayService.deleteAcsV6AppgroupHolidayByIds(ids));
    }
}
