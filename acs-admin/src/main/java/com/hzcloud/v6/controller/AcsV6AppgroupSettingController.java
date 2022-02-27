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
import com.hzcloud.v6.domain.AcsV6AppgroupSetting;
import com.hzcloud.v6.service.IAcsV6AppgroupSettingService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 基本设置Controller
 * 
 * @author zhangfan
 * @date 2021-07-16
 */
@RestController
@RequestMapping("/appgroup/setting")
public class AcsV6AppgroupSettingController extends BaseController
{
    @Autowired
    private IAcsV6AppgroupSettingService acsV6AppgroupSettingService;

    /**
     * 查询基本设置列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:setting:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsV6AppgroupSetting acsV6AppgroupSetting)
    {
        startPage();
        List<AcsV6AppgroupSetting> list = acsV6AppgroupSettingService.selectAcsV6AppgroupSettingList(acsV6AppgroupSetting);
        return getDataTable(list);
    }

    /**
     * 导出基本设置列表
     */
    @PreAuthorize("@ss.hasPermi('appgroup:setting:export')")
    @Log(title = "基本设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsV6AppgroupSetting acsV6AppgroupSetting)
    {
        List<AcsV6AppgroupSetting> list = acsV6AppgroupSettingService.selectAcsV6AppgroupSettingList(acsV6AppgroupSetting);
        ExcelUtil<AcsV6AppgroupSetting> util = new ExcelUtil<AcsV6AppgroupSetting>(AcsV6AppgroupSetting.class);
        return util.exportExcel(list, "基本设置数据");
    }

    /**
     * 获取基本设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('appgroup:setting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(acsV6AppgroupSettingService.selectAcsV6AppgroupSettingById(id));
    }

    /**
     * 新增基本设置
     */
    @PreAuthorize("@ss.hasPermi('appgroup:setting:add')")
    @Log(title = "基本设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsV6AppgroupSetting acsV6AppgroupSetting)
    {
        return toAjax(acsV6AppgroupSettingService.insertAcsV6AppgroupSetting(acsV6AppgroupSetting));
    }

    /**
     * 修改基本设置
     */
    @PreAuthorize("@ss.hasPermi('appgroup:setting:edit')")
    @Log(title = "基本设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsV6AppgroupSetting acsV6AppgroupSetting)
    {
        return toAjax(acsV6AppgroupSettingService.updateAcsV6AppgroupSetting(acsV6AppgroupSetting));
    }

    /**
     * 删除基本设置
     */
    @PreAuthorize("@ss.hasPermi('appgroup:setting:remove')")
    @Log(title = "基本设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(acsV6AppgroupSettingService.deleteAcsV6AppgroupSettingByIds(ids));
    }
}
