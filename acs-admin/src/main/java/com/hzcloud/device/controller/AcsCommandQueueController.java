package com.hzcloud.device.controller;

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
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.service.IAcsCommandQueueService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 命令任务Controller
 * 
 * @author ruoyi
 * @date 2021-05-31
 */
@RestController
@RequestMapping("/device/queue")
public class AcsCommandQueueController extends BaseController
{
    @Autowired
    private IAcsCommandQueueService acsCommandQueueService;

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;

    /**
     * 查询命令任务列表
     */
    @PreAuthorize("@ss.hasPermi('device:queue:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsCommandQueue acsCommandQueue)
    {
        startPage();
        List<AcsCommandQueue> list = acsCommandQueueService.selectAcsCommandQueueList(acsCommandQueue);
        return getDataTable(list);
    }

    /**
     * 查询命令任务列表
     */
    @PreAuthorize("@ss.hasPermi('device:queue:list')")
    @GetMapping("/listdf")
    public TableDataInfo listdf(AcsCommandQueue acsCommandQueue)
    {
        startPage();
        List<AcsCommandQueue> list = acsCommandQueueService.selectAcsCommandQueueListDataFilter(acsCommandQueue);
        return getDataTable(list);
    }

    /**
     * 导出命令任务列表
     */
    @PreAuthorize("@ss.hasPermi('device:queue:export')")
    @Log(title = "命令任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsCommandQueue acsCommandQueue)
    {
        List<AcsCommandQueue> list = acsCommandQueueService.selectAcsCommandQueueList(acsCommandQueue);
        ExcelUtil<AcsCommandQueue> util = new ExcelUtil<AcsCommandQueue>(AcsCommandQueue.class);
        return util.exportExcel(list, "命令任务数据");
    }

    /**
     * 获取命令任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('device:queue:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(acsCommandQueueService.selectAcsCommandQueueById(taskId));
    }

    /**
     * 新增命令任务
     */
    @PreAuthorize("@ss.hasPermi('device:queue:add')")
    @Log(title = "命令任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsCommandQueue acsCommandQueue)
    {
        return toAjax(acsCommandQueueService.insertAcsCommandQueue(acsCommandQueue));
    }

    /**
     * 修改命令任务
     */
    @PreAuthorize("@ss.hasPermi('device:queue:edit')")
    @Log(title = "命令任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsCommandQueue acsCommandQueue)
    {
        return toAjax(acsCommandQueueService.updateAcsCommandQueue(acsCommandQueue));
    }

    /**
     * 删除命令任务
     */
    @PreAuthorize("@ss.hasPermi('device:queue:remove')")
    @Log(title = "命令任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(acsCommandQueueService.deleteAcsCommandQueueByIds(taskIds));
    }

    /**
     * 重新执行失败命令
     */
    @PreAuthorize("@ss.hasPermi('device:queue:replay')")
    @Log(title = "命令任务", businessType = BusinessType.OTHER)
    @GetMapping("/replay")
    public AjaxResult replay(){
        deviceControllerQueueService.replay();
        return AjaxResult.success();
    }
}
