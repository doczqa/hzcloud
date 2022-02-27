package com.hzcloud.group.mapper;

import com.hzcloud.group.domain.AcsHolidayTimeSlice;

/**
 * 假日时间段Mapper接口
 * 
 * @author zhangfan
 * @date 2021-06-16
 */
public interface AcsHolidayTimeSliceMapper 
{
    /**
     * 新增假日时间段
     * 
     * @param acsHolidayTimeSlice 假日时间段
     * @return 结果
     */
    public int insertAcsHolidayTimeSlice(AcsHolidayTimeSlice acsHolidayTimeSlice);

    /**
     * 查询假日时间段
     * @return 结果
     */
    public AcsHolidayTimeSlice selectAcsHolidayTimeSlice();
}
