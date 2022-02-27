package com.hzcloud.group.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.group.mapper.AcsHolidayTimeSliceMapper;
import com.hzcloud.group.domain.AcsHolidayTimeSlice;
import com.hzcloud.group.service.IAcsHolidayTimeSliceService;

/**
 * 假日时间段Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-06-16
 */
@Service
public class AcsHolidayTimeSliceServiceImpl implements IAcsHolidayTimeSliceService 
{
    @Autowired
    private AcsHolidayTimeSliceMapper acsHolidayTimeSliceMapper;

    /**
     * 新增或修改假日时间段
     * 
     * @param acsHolidayTimeSlice 假日时间段
     * @return 结果
     */
    @Override
    public int insertAcsHolidayTimeSlice(AcsHolidayTimeSlice acsHolidayTimeSlice)
    {
        return acsHolidayTimeSliceMapper.insertAcsHolidayTimeSlice(acsHolidayTimeSlice);
    }

    /**
     * 获取假日时间段
     * @return
     */
    @Override
    public AcsHolidayTimeSlice selectAcsHolidayTimeSlice(){
        return acsHolidayTimeSliceMapper.selectAcsHolidayTimeSlice();
    }
}
