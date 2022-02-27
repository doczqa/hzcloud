package com.hzcloud.device.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.hzcloud.device.domain.AcsDeviceZoneCon;

/**
 * 区域与控制器关联表 数据层
 * 
 * @author zhangfan
 */
public interface AcsDeviceZoneConMapper {
    /**
     * 通过区域ID删除区域和控制器关联
     * 
     * @param zoneId 用户ID
     * @return 结果
     */
    public int deleteZoneConByZoneId(Long zoneId);

    /**
     * 批量删除区域和控制器关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteZoneCon(Long[] ids);

    /**
     * 通过控制器ID查询控制器使用数量
     * 
     * @param controllerId 角色ID
     * @return 结果
     */
    public int countZoneConByControllerId(Long controllerId);

    /**
     * 批量新增用户角色信息
     * 
     * @param zonecontrollerList 用户角色列表
     * @return 结果
     */
    public int batchZoneCon(List<AcsDeviceZoneCon> zoneControllerList);

    /**
     * 删除用户和角色关联信息
     * 
     * @param zonecontroller 用户和角色关联信息
     * @return 结果
     */
    public int deleteZoneConInfo(AcsDeviceZoneCon zonecontroller);

    /**
     * 批量取消授权用户角色
     * 
     * @param controllerId 角色ID
     * @param zoneIds 需要删除的用户数据ID
     * @return 结果
     */
    public int deleteZoneConInfos(@Param("controllerId") Long controllerId, @Param("zoneIds") Long[] zoneIds);
}
