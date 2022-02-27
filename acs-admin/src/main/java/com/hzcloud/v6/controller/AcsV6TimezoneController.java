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
import com.hzcloud.v6.domain.AcsV6Timezone;
import com.hzcloud.v6.service.IAcsV6TimezoneService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 时区设置Controller
 * 
 * @author zhangfan
 * @date 2021-07-14
 */
@RestController
@RequestMapping("/timezone/timezone")
public class AcsV6TimezoneController extends BaseController
{
    @Autowired
    private IAcsV6TimezoneService acsV6TimezoneService;

    /**
     * 查询时区设置列表
     */
    @PreAuthorize("@ss.hasPermi('timezone:timezone:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsV6Timezone acsV6Timezone)
    {
        startPage();
        List<AcsV6Timezone> list = acsV6TimezoneService.selectAcsV6TimezoneList(acsV6Timezone);
        return getDataTable(list);
    }

    /**
     * 导出时区设置列表
     */
    @PreAuthorize("@ss.hasPermi('timezone:timezone:export')")
    @Log(title = "时区设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsV6Timezone acsV6Timezone)
    {
        List<AcsV6Timezone> list = acsV6TimezoneService.selectAcsV6TimezoneList(acsV6Timezone);
        ExcelUtil<AcsV6Timezone> util = new ExcelUtil<AcsV6Timezone>(AcsV6Timezone.class);
        return util.exportExcel(list, "时区设置数据");
    }

    /**
     * 获取时区设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('timezone:timezone:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(acsV6TimezoneService.selectAcsV6TimezoneById(id));
    }

    /**
     * 新增时区设置
     */
    @PreAuthorize("@ss.hasPermi('timezone:timezone:add')")
    @Log(title = "时区设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsV6Timezone acsV6Timezone)
    {
        return toAjax(acsV6TimezoneService.insertAcsV6Timezone(acsV6Timezone));
    }

    /**
     * 修改时区设置
     */
    @PreAuthorize("@ss.hasPermi('timezone:timezone:edit')")
    @Log(title = "时区设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsV6Timezone acsV6Timezone)
    {
        return toAjax(acsV6TimezoneService.updateAcsV6Timezone(acsV6Timezone));
    }

    /**
     * 删除时区设置
     */
    @PreAuthorize("@ss.hasPermi('timezone:timezone:remove')")
    @Log(title = "时区设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(acsV6TimezoneService.deleteAcsV6TimezoneByIds(ids));
    }
}
