package com.hzcloud.device.mapper;

import java.util.List;
import com.hzcloud.device.domain.AcsDeviceZone;

/**
 * 区域Mapper接口
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
public interface AcsDeviceZoneMapper 
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
     * 删除区域
     * 
     * @param zoneId 区域ID
     * @return 结果
     */
    public int deleteAcsDeviceZoneById(Long zoneId);

    /**
     * 批量删除区域
     * 
     * @param zoneIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsDeviceZoneByIds(Long[] zoneIds);
    
    /**
     * 校验区域名称是否唯一
     * 
     * @param zoneName 用户名称
     * @return 结果
     */
    public int checkZoneNameUnique(String zoneName);
}
