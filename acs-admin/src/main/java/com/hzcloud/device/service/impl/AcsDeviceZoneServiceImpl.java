package com.hzcloud.device.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.hzcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.common.annotation.DataScope;
import com.hzcloud.device.mapper.AcsDeviceZoneConMapper;
import com.hzcloud.device.mapper.AcsDeviceZoneMapper;
import com.hzcloud.device.domain.AcsDeviceZone;
import com.hzcloud.device.domain.AcsDeviceZoneCon;
import com.hzcloud.device.service.IAcsDeviceZoneService;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.utils.StringUtils;

/**
 * 区域Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
@Service
public class AcsDeviceZoneServiceImpl implements IAcsDeviceZoneService 
{
    @Autowired
    private AcsDeviceZoneMapper acsDeviceZoneMapper;

    @Autowired
    private AcsDeviceZoneConMapper acsDeviceZoneConMapper;

    /**
     * 查询区域
     * 
     * @param zoneId 区域ID
     * @return 区域
     */
    @Override
    public AcsDeviceZone selectAcsDeviceZoneById(Long zoneId)
    {
        return acsDeviceZoneMapper.selectAcsDeviceZoneById(zoneId);
    }

    /**
     * 查询区域列表
     * 
     * @param acsDeviceZone 区域
     * @return 区域
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<AcsDeviceZone> selectAcsDeviceZoneList(AcsDeviceZone acsDeviceZone)
    {
        return acsDeviceZoneMapper.selectAcsDeviceZoneList(acsDeviceZone);
    }

    /**
     * 新增区域
     * 
     * @param acsDeviceZone 区域
     * @return 结果
     */
    @Override
    public int insertAcsDeviceZone(AcsDeviceZone acsDeviceZone)
    {
        acsDeviceZone.setCreateTime(DateUtils.getNowDate());
        int rows = acsDeviceZoneMapper.insertAcsDeviceZone(acsDeviceZone);
        // 新增区域与控制器管理
        insertZoneController(acsDeviceZone);
        return rows;
    }

    /**
     * 修改区域
     * 
     * @param acsDeviceZone 区域
     * @return 结果
     */
    @Override
    public int updateAcsDeviceZone(AcsDeviceZone acsDeviceZone)
    {
        Long zoneId = acsDeviceZone.getZoneId();
        // 删除区域与控制器关联
        acsDeviceZoneConMapper.deleteZoneConByZoneId(zoneId);
        // 新增区域与控制器管理
        insertZoneController(acsDeviceZone);
        acsDeviceZone.setUpdateTime(DateUtils.getNowDate());
        return acsDeviceZoneMapper.updateAcsDeviceZone(acsDeviceZone);
    }

    /**
     * 批量删除区域
     * 
     * @param zoneIds 需要删除的区域ID
     * @return 结果
     */
    @Override
    public int deleteAcsDeviceZoneByIds(Long[] zoneIds)
    {
        // 删除区域与控制器关联
        acsDeviceZoneConMapper.deleteZoneCon(zoneIds);
        return acsDeviceZoneMapper.deleteAcsDeviceZoneByIds(zoneIds);
    }

    /**
     * 删除区域信息
     * 
     * @param zoneId 区域ID
     * @return 结果
     */
    @Override
    public int deleteAcsDeviceZoneById(Long zoneId)
    {
        // 删除区域与控制器关联
        acsDeviceZoneConMapper.deleteZoneConByZoneId(zoneId);
        return acsDeviceZoneMapper.deleteAcsDeviceZoneById(zoneId);
    }

    /**
     * 校验区域名称是否唯一
     * 
     * @param zoneName 用户名称
     * @return 结果
     */
    @Override
    public String checkZoneNameUnique(String zoneName)
    {
        int count = acsDeviceZoneMapper.checkZoneNameUnique(zoneName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 修改区域状态
     * 
     * @param zone 用户信息
     * @return 结果
     */
    @Override
    public int updateZoneStatus(AcsDeviceZone zone)
    {
        return acsDeviceZoneMapper.updateAcsDeviceZone(zone);
    }

    /**
     * 新增区域控制器信息
     * 
     * @param zone 区域对象
     */
    public void insertZoneController(AcsDeviceZone zone)
    {
        Long[] controllers = zone.getControllerIds();
        if (StringUtils.isNotNull(controllers))
        {
            // 新增用户与角色管理
            List<AcsDeviceZoneCon> list = new ArrayList<AcsDeviceZoneCon>();
            for (Long controllerId : controllers)
            {
                AcsDeviceZoneCon zc = new AcsDeviceZoneCon();
                zc.setZoneId(zone.getZoneId());
                zc.setControllerId(controllerId);
                list.add(zc);
            }
            if (list.size() > 0)
            {
                acsDeviceZoneConMapper.batchZoneCon(list);
            }
        }
    }
}
