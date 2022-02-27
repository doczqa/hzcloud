package com.hzcloud.info.mapper;

import java.util.List;
import com.hzcloud.info.domain.AcsAlarmInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
 * 报警信息Mapper接口
 * 
 * @author zhangfan
 * @date 2021-05-17
 */
@Repository
public interface AcsAlarmInfoMapper 
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
     * 删除报警信息
     * 
     * @param alarmId 报警信息ID
     * @return 结果
     */
    public int deleteAcsAlarmInfoById(Long alarmId);

    /**
     * 批量删除报警信息
     * 
     * @param alarmIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsAlarmInfoByIds(Long[] alarmIds);

    /**
     * 获取报警信息
     * @param list 控制器Id列表
     * @param doorPin 门端口
     * @return
     */
    public List<AcsAlarmInfo> selectAcsAlarmInfoListByConAndDoor(@Param("doorPin") Long doorPin, @Param("list") List<Long> list);
}
