package com.hzcloud.group.service;

import com.hzcloud.group.domain.AcsHolidayTimeSlice;

/**
 * 假日时间段Service接口
 * 
 * @author zhangfan
 * @date 2021-06-16
 */
public interface IAcsHolidayTimeSliceService 
{
    /**
     * 新增或修改假日时间段
     * 
     * @param acsHolidayTimeSlice 假日时间段
     * @return 结果
     */
    public int insertAcsHolidayTimeSlice(AcsHolidayTimeSlice acsHolidayTimeSlice);

    /**
     * 获取假日时间段
     * @return
     */
    public AcsHolidayTimeSlice selectAcsHolidayTimeSlice();
}
