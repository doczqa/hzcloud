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
import com.hzcloud.v6.domain.AcsV6Interval;
import com.hzcloud.v6.service.IAcsV6IntervalService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 时段设置Controller
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
@RestController
@RequestMapping("/timezone/interval")
public class AcsV6IntervalController extends BaseController
{
    @Autowired
    private IAcsV6IntervalService acsV6IntervalService;

    /**
     * 查询时段设置列表
     */
    @PreAuthorize("@ss.hasPermi('timezone:interval:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsV6Interval acsV6Interval)
    {
        startPage();
        List<AcsV6Interval> list = acsV6IntervalService.selectAcsV6IntervalList(acsV6Interval);
        return getDataTable(list);
    }

    /**
     * 导出时段设置列表
     */
    @PreAuthorize("@ss.hasPermi('timezone:interval:export')")
    @Log(title = "时段设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsV6Interval acsV6Interval)
    {
        List<AcsV6Interval> list = acsV6IntervalService.selectAcsV6IntervalList(acsV6Interval);
        ExcelUtil<AcsV6Interval> util = new ExcelUtil<AcsV6Interval>(AcsV6Interval.class);
        return util.exportExcel(list, "时段设置数据");
    }

    /**
     * 获取时段设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('timezone:interval:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(acsV6IntervalService.selectAcsV6IntervalById(id));
    }

    /**
     * 新增时段设置
     */
    @PreAuthorize("@ss.hasPermi('timezone:interval:add')")
    @Log(title = "时段设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsV6Interval acsV6Interval)
    {
        return toAjax(acsV6IntervalService.insertAcsV6Interval(acsV6Interval));
    }

    /**
     * 修改时段设置
     */
    @PreAuthorize("@ss.hasPermi('timezone:interval:edit')")
    @Log(title = "时段设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsV6Interval acsV6Interval)
    {
        return toAjax(acsV6IntervalService.updateAcsV6Interval(acsV6Interval));
    }

    /**
     * 删除时段设置
     */
    @PreAuthorize("@ss.hasPermi('timezone:interval:remove')")
    @Log(title = "时段设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(acsV6IntervalService.deleteAcsV6IntervalByIds(ids));
    }
}
