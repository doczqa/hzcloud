package com.hzcloud.device.service.impl;

import java.util.List;

import com.hzcloud.common.annotation.DataScope;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.device.mapper.AcsDeviceConMapper;
import com.hzcloud.device.mapper.AcsDeviceDoorMapper;
import com.hzcloud.device.AcsDeviceController;
import com.hzcloud.device.AcsDeviceControllerFactory;
import com.hzcloud.device.config.DeviceConStatus;
import com.hzcloud.device.config.DeviceConfig;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.v6.service.IAcsV6CardIndexService;
import com.hzcloud.common.exception.CustomException;

/**
 * 门禁控制器Service业务层处理
 * 
 * @author 张帆
 * @date 2021-04-26
 */
@Service
public class AcsDeviceConServiceImpl implements IAcsDeviceConService 
{
    @Autowired
    private AcsDeviceConMapper acsDeviceConMapper;

    @Autowired
    private DeviceConfig deviceConfig;

    @Autowired
    private IAcsV6CardIndexService cardIndexService;

    @Autowired
    private AcsDeviceDoorMapper acsDeviceDoorMapper;

    /**
     * 查询门禁控制器
     * 
     * @param controllerId 门禁控制器ID
     * @return 门禁控制器
     */
    @Override
    public AcsDeviceCon selectAcsDeviceConById(Long controllerId)
    {
        return acsDeviceConMapper.selectAcsDeviceConById(controllerId);
    }

    /**
     * 查询门禁控制器列表
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 门禁控制器
     */
    @Override
    public List<AcsDeviceCon> selectAcsDeviceConList(AcsDeviceCon acsDeviceCon)
    {
        return acsDeviceConMapper.selectAcsDeviceConList(acsDeviceCon);
    }

    @Override
    public List<AcsDeviceCon> selectAcsDeviceConListByZoneId(Long zoneId) {
        return acsDeviceConMapper.selectAcsDeviceConListByZoneId(zoneId);
    }

    /**
     * 查询门禁控制器列表过滤
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<AcsDeviceCon> selectAcsDeviceConListDataFilter(AcsDeviceCon acsDeviceCon){
        return acsDeviceConMapper.selectAcsDeviceConListDataFilter(acsDeviceCon);
    }

    /**
     * 新增门禁控制器
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    @Override
    public int insertAcsDeviceCon(AcsDeviceCon acsDeviceCon)
    {
        int capacity = acsDeviceCon.getCapacity();
        acsDeviceCon.setCreateTime(DateUtils.getNowDate());
        int ret = acsDeviceConMapper.insertAcsDeviceCon(acsDeviceCon);
        if (ret == 1){
            new Thread(new BatchThread(acsDeviceCon.getControllerId(), 1, capacity)).start();
            if(!DeviceConStatus.getMap().containsKey(acsDeviceCon.getIp())){
                AcsDeviceController deviceController = AcsDeviceControllerFactory.CreateController(acsDeviceCon.getType());
                DeviceConStatus.addDeviceController(acsDeviceCon.getIp(), deviceController);
                deviceController.Login(acsDeviceCon.getIp(),
                    acsDeviceCon.getUserName(),
                    acsDeviceCon.getPassword(),
                    Integer.parseInt(String.valueOf(acsDeviceCon.getPort())),
                    acsDeviceCon.getIdentifier());
                deviceConfig.StartNewMonitorThread(deviceController, acsDeviceCon.getIp());
            }
        }
        return ret;
    }

    /**
     * 修改门禁控制器
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    @Override
    public int updateAcsDeviceCon(AcsDeviceCon acsDeviceCon)
    {
        int capacity = acsDeviceCon.getCapacity();
        int oldcapacity = cardIndexService.getCardIndexCountofController(acsDeviceCon.getControllerId());
        if(capacity > oldcapacity){
            new Thread(new BatchThread(acsDeviceCon.getControllerId(), oldcapacity + 1, capacity - oldcapacity)).start();
        }
        acsDeviceCon.setUpdateTime(DateUtils.getNowDate());
        if(!DeviceConStatus.getMap().containsKey(acsDeviceCon.getIp())){
            AcsDeviceController deviceController = AcsDeviceControllerFactory.CreateController(acsDeviceCon.getType());
            DeviceConStatus.addDeviceController(acsDeviceCon.getIp(), deviceController);
            deviceController.Login(acsDeviceCon.getIp(),
                acsDeviceCon.getUserName(),
                acsDeviceCon.getPassword(),
                Integer.parseInt(String.valueOf(acsDeviceCon.getPort())),
                acsDeviceCon.getIdentifier());
            deviceConfig.StartNewMonitorThread(deviceController, acsDeviceCon.getIp());
        }
        return acsDeviceConMapper.updateAcsDeviceCon(acsDeviceCon);
    }

    /**
     * 修改门禁控制器
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    @Override
    public int updateAcsDeviceConAlarmStatus(AcsDeviceCon acsDeviceCon)
    {
        acsDeviceCon.setUpdateTime(DateUtils.getNowDate());
        return acsDeviceConMapper.updateAcsDeviceCon(acsDeviceCon);
    }

    /**
     * 批量删除门禁控制器
     * 
     * @param controllerIds 需要删除的门禁控制器ID
     * @return 结果
     */
    @Override
    public int deleteAcsDeviceConByIds(Long[] controllerIds)
    {
        for (Long controllerId : controllerIds)
        {
            AcsDeviceCon controller = selectAcsDeviceConById(controllerId);
            if (acsDeviceDoorMapper.countDoorByControllerId(controllerId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", controller.getControllerName()));
            }
        }
        cardIndexService.deleteAcsV6CardIndexByIds(controllerIds);
        return acsDeviceConMapper.deleteAcsDeviceConByIds(controllerIds);
    }

    /**
     * 删除门禁控制器信息
     * 
     * @param controllerId 门禁控制器ID
     * @return 结果
     */
    @Override
    public int deleteAcsDeviceConById(Long controllerId)
    {
        cardIndexService.deleteAcsV6CardIndexById(controllerId);
        return acsDeviceConMapper.deleteAcsDeviceConById(controllerId);
    }

    /**
     * 根据区域ID获取控制器选择框列表
     * 
     * @param zoneId 区域ID
     * @return 选中控制器ID列表
     */
    @Override
    public List<Long> selectConListByZoneId(Long zoneId)
    {
        return acsDeviceConMapper.selectConListByZoneId(zoneId);
    }

    @Override
    public List<AcsDeviceCon> selectConByIp(String ip) {
        return acsDeviceConMapper.selectConByIp(ip);
    }

    @Override
    public int updateConStatus(AcsDeviceCon acsDeviceCon) {
        acsDeviceCon.setUpdateBy("system");
        acsDeviceCon.setUpdateTime(DateUtils.getNowDate());
        return acsDeviceConMapper.updateAcsDeviceCon(acsDeviceCon);
    }

    private class BatchThread implements Runnable{
        private volatile long controllerId;
        private volatile int index;
        private volatile int number;

        public BatchThread(long controllerId, int index,int number){
            this.controllerId = controllerId;
            this.index = index;
            this.number = number;
        }

        @Override
        public void run() {
            cardIndexService.batchCardIndex(controllerId, index, number);
        }
    }

    @Override
    public String checkControllerNameUnique(String controllerName) {
        int count = acsDeviceConMapper.checkControllerNameUnique(controllerName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
