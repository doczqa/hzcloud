package com.hzcloud.device.service;

import java.util.List;
import com.hzcloud.device.domain.AcsDeviceZone;

/**
 * 区域Service接口
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
public interface IAcsDeviceZoneService 
{
    /**
     * 查询区域
     * 
     * @param zoneId 区域ID
     * @return 区域
     */
    public AcsDeviceZone selectAcsDeviceZoneById(Long zoneId);

    /**
     * 查询区域列表
     * 
     * @param acsDeviceZone 区域
     * @return 区域集合
     */
    public List<AcsDeviceZone> selectAcsDeviceZoneList(AcsDeviceZone acsDeviceZone);

    /**
     * 新增区域
     * 
     * @param acsDeviceZone 区域
     * @return 结果
     */
    public int insertAcsDeviceZone(AcsDeviceZone acsDeviceZone);

    /**
     * 修改区域
     * 
     * @param acsDeviceZone 区域
     * @return 结果
     */
    public int updateAcsDeviceZone(AcsDeviceZone acsDeviceZone);

    /**
     * 批量删除区域
     * 
     * @param zoneIds 需要删除的区域ID
     * @return 结果
     */
    public int deleteAcsDeviceZoneByIds(Long[] zoneIds);

    /**
     * 删除区域信息
     * 
     * @param zoneId 区域ID
     * @return 结果
     */
    public int deleteAcsDeviceZoneById(Long zoneId);
    
    /**
     * 校验区域名称是否唯一
     * 
     * @param zoneName 区域名称
     * @return 结果
     */
    public String checkZoneNameUnique(String zoneName);

    /**
     * 修改区域状态
     * 
     * @param zone 区域信息
     * @return 结果
     */
    public int updateZoneStatus(AcsDeviceZone zone);
}
