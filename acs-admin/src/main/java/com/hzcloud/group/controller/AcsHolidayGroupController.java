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
import com.hzcloud.group.domain.AcsHolidayGroup;
import com.hzcloud.group.service.IAcsHolidayGroupService;
import com.hzcloud.group.service.IAcsHolidayPlanService;
import com.hzcloud.common.utils.StringUtils;
import com.hzcloud.common.utils.poi.ExcelUtil;
import com.hzcloud.common.core.page.TableDataInfo;

/**
 * 假日组Controller
 * 
 * @author zhangfan
 * @date 2021-05-13
 */
@RestController
@RequestMapping("/holiday/group")
public class AcsHolidayGroupController extends BaseController
{
    @Autowired
    private IAcsHolidayGroupService acsHolidayGroupService;

    @Autowired
    private IAcsHolidayPlanService acsHolidayPlanService;

    /**
     * 查询假日组列表
     */
    @PreAuthorize("@ss.hasPermi('holiday:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(AcsHolidayGroup acsHolidayGroup)
    {
        startPage();
        List<AcsHolidayGroup> list = acsHolidayGroupService.selectAcsHolidayGroupList(acsHolidayGroup);
        return getDataTable(list);
    }

    /**
     * 导出假日组列表
     */
    @PreAuthorize("@ss.hasPermi('holiday:group:export')")
    @Log(title = "假日组", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(AcsHolidayGroup acsHolidayGroup)
    {
        List<AcsHolidayGroup> list = acsHolidayGroupService.selectAcsHolidayGroupList(acsHolidayGroup);
        ExcelUtil<AcsHolidayGroup> util = new ExcelUtil<AcsHolidayGroup>(AcsHolidayGroup.class);
        return util.exportExcel(list, "假日组数据");
    }

    /**
     * 获取假日组详细信息
     */
    @PreAuthorize("@ss.hasPermi('holiday:group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") Long groupId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(groupId))
        {
            AcsHolidayGroup group = acsHolidayGroupService.selectAcsHolidayGroupById(groupId);
            List<Long> planList = acsHolidayPlanService.selectPlanListByGroupId(groupId);
            Long[] planIds = new Long[planList.size()];
            planIds = planList.toArray(planIds);
            group.setPlanIds(planIds);
            ajax.put(AjaxResult.DATA_TAG, group);
        }
        return ajax;
    }

    /**
     * 新增假日组
     */
    @PreAuthorize("@ss.hasPermi('holiday:group:add')")
    @Log(title = "假日组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsHolidayGroup acsHolidayGroup)
    {
        return toAjax(acsHolidayGroupService.insertAcsHolidayGroup(acsHolidayGroup));
    }

    /**
     * 修改假日组
     */
    @PreAuthorize("@ss.hasPermi('holiday:group:edit')")
    @Log(title = "假日组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AcsHolidayGroup acsHolidayGroup)
    {
        return toAjax(acsHolidayGroupService.updateAcsHolidayGroup(acsHolidayGroup));
    }

    /**
     * 删除假日组
     */
    @PreAuthorize("@ss.hasPermi('holiday:group:remove')")
    @Log(title = "假日组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable Long[] groupIds)
    {
        return toAjax(acsHolidayGroupService.deleteAcsHolidayGroupByIds(groupIds));
    }
}
