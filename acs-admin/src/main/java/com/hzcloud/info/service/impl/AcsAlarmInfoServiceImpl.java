package com.hzcloud.info.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.info.mapper.AcsAlarmInfoMapper;
import com.hzcloud.info.domain.AcsAlarmInfo;
import com.hzcloud.info.service.IAcsAlarmInfoService;

/**
 * 报警信息Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-05-17
 */
@Service
public class AcsAlarmInfoServiceImpl implements IAcsAlarmInfoService 
{
    @Autowired
    private AcsAlarmInfoMapper acsAlarmInfoMapper;

    /**
     * 查询报警信息
     * 
     * @param alarmId 报警信息ID
     * @return 报警信息
     */
    @Override
    public AcsAlarmInfo selectAcsAlarmInfoById(Long alarmId)
    {
        return acsAlarmInfoMapper.selectAcsAlarmInfoById(alarmId);
    }

    /**
     * 查询报警信息列表
     * 
     * @param acsAlarmInfo 报警信息
     * @return 报警信息
     */
    @Override
    public List<AcsAlarmInfo> selectAcsAlarmInfoList(AcsAlarmInfo acsAlarmInfo)
    {
        return acsAlarmInfoMapper.selectAcsAlarmInfoList(acsAlarmInfo);
    }

    /**
     * 新增报警信息
     * 
     * @param acsAlarmInfo 报警信息
     * @return 结果
     */
    @Override
    public int insertAcsAlarmInfo(AcsAlarmInfo acsAlarmInfo)
    {
        return acsAlarmInfoMapper.insertAcsAlarmInfo(acsAlarmInfo);
    }

    /**
     * 修改报警信息
     * 
     * @param acsAlarmInfo 报警信息
     * @return 结果
     */
    @Override
    public int updateAcsAlarmInfo(AcsAlarmInfo acsAlarmInfo)
    {
        return acsAlarmInfoMapper.updateAcsAlarmInfo(acsAlarmInfo);
    }

    /**
     * 批量删除报警信息
     * 
     * @param alarmIds 需要删除的报警信息ID
     * @return 结果
     */
    @Override
    public int deleteAcsAlarmInfoByIds(Long[] alarmIds)
    {
        return acsAlarmInfoMapper.deleteAcsAlarmInfoByIds(alarmIds);
    }

    /**
     * 删除报警信息信息
     * 
     * @param alarmId 报警信息ID
     * @return 结果
     */
    @Override
    public int deleteAcsAlarmInfoById(Long alarmId)
    {
        return acsAlarmInfoMapper.deleteAcsAlarmInfoById(alarmId);
    }

    @Override
    public List<AcsAlarmInfo> selectAcsAlarmInfoListByConAndDoor(Long doorPin, List<Long> list){
        return acsAlarmInfoMapper.selectAcsAlarmInfoListByConAndDoor(doorPin, list);
    }
}
