package com.hzcloud.device.service;

import java.util.List;
import com.hzcloud.device.domain.AcsDeviceCon;

/**
 * 门禁控制器Service接口
 * 
 * @author 张帆
 * @date 2021-04-26
 */
public interface IAcsDeviceConService 
{
    /**
     * 查询门禁控制器
     * 
     * @param controllerId 门禁控制器ID
     * @return 门禁控制器
     */
    public AcsDeviceCon selectAcsDeviceConById(Long controllerId);

    /**
     * 查询门禁控制器列表
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 门禁控制器集合
     */
    public List<AcsDeviceCon> selectAcsDeviceConList(AcsDeviceCon acsDeviceCon);

    /**
     * 根据区域ID查询门禁控制器列表
     * @param zoneId 区域ID
     * @return
     */
    public List<AcsDeviceCon> selectAcsDeviceConListByZoneId(Long zoneId);

    /**
     * 查询门禁控制器列表过滤
     * @param acsDeviceCon
     * @return
     */
    public List<AcsDeviceCon> selectAcsDeviceConListDataFilter(AcsDeviceCon acsDeviceCon);

    /**
     * 新增门禁控制器
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    public int insertAcsDeviceCon(AcsDeviceCon acsDeviceCon);

    /**
     * 修改门禁控制器
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    public int updateAcsDeviceCon(AcsDeviceCon acsDeviceCon);

    /**
     * 修改门禁控制器布防状态
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    public int updateAcsDeviceConAlarmStatus(AcsDeviceCon acsDeviceCon);


    /**
     * 批量删除门禁控制器
     * 
     * @param controllerIds 需要删除的门禁控制器ID
     * @return 结果
     */
    public int deleteAcsDeviceConByIds(Long[] controllerIds);

    /**
     * 删除门禁控制器信息
     * 
     * @param controllerId 门禁控制器ID
     * @return 结果
     */
    public int deleteAcsDeviceConById(Long controllerId);

    /**
     * 根据区域ID获取控制器选择框列表
     * 
     * @param zoneId 区域ID
     * @return 选中控制器ID列表
     */
    public List<Long> selectConListByZoneId(Long zoneId);

    /**
     * 根据IP获取控制器
     * 
     * @param ip 控制器IP
     * @return 控制器
     */
    public List<AcsDeviceCon> selectConByIp(String ip);

    /**
     * 更新控制器状态
     * @param acsDeviceCon
     * @return
     */
    public int updateConStatus(AcsDeviceCon acsDeviceCon);

    /**
     * 校验控制器名称是否唯一
     * 
     * @param controllerName 用户名称
     * @return 结果
     */
    public String checkControllerNameUnique(String controllerName);
}
