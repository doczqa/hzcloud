package com.hzcloud.info.service;

import java.util.List;
import com.hzcloud.info.domain.AcsAlarmInfo;

/**
 * 报警信息Service接口
 * 
 * @author zhangfan
 * @date 2021-05-17
 */
public interface IAcsAlarmInfoService 
{
    /**
     * 查询报警信息
     * 
     * @param alarmId 报警信息ID
     * @return 报警信息
     */
    public AcsAlarmInfo selectAcsAlarmInfoById(Long alarmId);

    /**
     * 查询报警信息列表
     * 
     * @param acsAlarmInfo 报警信息
     * @return 报警信息集合
     */
    public List<AcsAlarmInfo> selectAcsAlarmInfoList(AcsAlarmInfo acsAlarmInfo);

    /**
     * 新增报警信息
     * 
     * @param acsAlarmInfo 报警信息
     * @return 结果
     */
    public int insertAcsAlarmInfo(AcsAlarmInfo acsAlarmInfo);

    /**
     * 修改报警信息
     * 
     * @param acsAlarmInfo 报警信息
     * @return 结果
     */
    public int updateAcsAlarmInfo(AcsAlarmInfo acsAlarmInfo);

    /**
     * 批量删除报警信息
     * 
     * @param alarmIds 需要删除的报警信息ID
     * @return 结果
     */
    public int deleteAcsAlarmInfoByIds(Long[] alarmIds);

    /**
     * 删除报警信息信息
     * 
     * @param alarmId 报警信息ID
     * @return 结果
     */
    public int deleteAcsAlarmInfoById(Long alarmId);

    /**
     * 根据控制器和门端口获取报警信息
     */
    public List<AcsAlarmInfo> selectAcsAlarmInfoListByConAndDoor(Long doorPin, List<Long> list);
}
