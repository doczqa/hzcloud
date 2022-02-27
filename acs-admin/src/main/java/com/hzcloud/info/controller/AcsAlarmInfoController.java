package com.hzcloud.info.controller;

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
import com.hzcloud.info.bo.ControllerAndDoor;
import com.hzcloud.info.domain.AcsAlarmInfo;
import com.hzcloud.info.service.IAcsAlarmInfoService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 报警信息Controller
 * 
 * @author zhangfan
 * @date 2021-05-17
 */
@RestController
@RequestMapping("/alarm/info")
public class AcsAlarmInfoController extends BaseController
{
    @Autowired
    private IAcsAlarmInfoService acsAlarmInfoService;

    /**
     * 查询报警信息列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsAlarmInfo acsAlarmInfo)
    {
        startPage();
        List<AcsAlarmInfo> list = acsAlarmInfoService.selectAcsAlarmInfoList(acsAlarmInfo);
        return getDataTable(list);
    }

    @GetMapping("/listbyconanddoor")
    public TableDataInfo listByConAndDoor(ControllerAndDoor conAndDoor )
    {
        startPage();
        List<AcsAlarmInfo> list = acsAlarmInfoService.selectAcsAlarmInfoListByConAndDoor(conAndDoor.getDoorPin(), conAndDoor.getControllerIds());
        return getDataTable(list);
    }

    /**
     * 导出报警信息列表
     */
    @PreAuthorize("@ss.hasPermi('alarm:info:export')")
    @Log(title = "报警信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsAlarmInfo acsAlarmInfo)
    {
        List<AcsAlarmInfo> list = acsAlarmInfoService.selectAcsAlarmInfoList(acsAlarmInfo);
        ExcelUtil<AcsAlarmInfo> util = new ExcelUtil<AcsAlarmInfo>(AcsAlarmInfo.class);
        return util.exportExcel(list, "报警信息数据");
    }

    /**
     * 获取报警信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:info:query')")
    @GetMapping(value = "/{alarmId}")
    public AjaxResult getInfo(@PathVariable("alarmId") Long alarmId)
    {
        return AjaxResult.success(acsAlarmInfoService.selectAcsAlarmInfoById(alarmId));
    }

    /**
     * 新增报警信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:info:add')")
    @Log(title = "报警信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsAlarmInfo acsAlarmInfo)
    {
        return toAjax(acsAlarmInfoService.insertAcsAlarmInfo(acsAlarmInfo));
    }

    /**
     * 修改报警信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:info:edit')")
    @Log(title = "报警信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsAlarmInfo acsAlarmInfo)
    {
        return toAjax(acsAlarmInfoService.updateAcsAlarmInfo(acsAlarmInfo));
    }

    /**
     * 删除报警信息
     */
    @PreAuthorize("@ss.hasPermi('alarm:info:remove')")
    @Log(title = "报警信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alarmIds}")
    public AjaxResult remove(@PathVariable Long[] alarmIds)
    {
        return toAjax(acsAlarmInfoService.deleteAcsAlarmInfoByIds(alarmIds));
    }
}
