package com.hzcloud.group.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hzcloud.common.annotation.Log;
import com.hzcloud.common.core.controller.BaseController;
import com.hzcloud.common.core.domain.AjaxResult;
import com.hzcloud.common.enums.BusinessType;
import com.hzcloud.group.domain.AcsHolidayTimeSlice;
import com.hzcloud.group.service.IAcsHolidayTimeSliceService;

/**
 * 假日时间段Controller
 * 
 * @author zhangfan
 * @date 2021-06-16
 */
@RestController
@RequestMapping("/holiday/slice")
public class AcsHolidayTimeSliceController extends BaseController
{
    @Autowired
    private IAcsHolidayTimeSliceService acsHolidayTimeSliceService;

    /**
     * 新增假日时间段
     */
    @PreAuthorize("@ss.hasPermi('group:plan:add')")
    @Log(title = "假日时间段", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AcsHolidayTimeSlice acsHolidayTimeSlice)
    {
        acsHolidayTimeSlice.setId((long) 1);
        return toAjax(acsHolidayTimeSliceService.insertAcsHolidayTimeSlice(acsHolidayTimeSlice));
    }

    @GetMapping
    public AjaxResult get(){
        return AjaxResult.success(acsHolidayTimeSliceService.selectAcsHolidayTimeSlice());
    }

}
