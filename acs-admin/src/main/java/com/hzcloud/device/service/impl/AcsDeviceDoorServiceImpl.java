package com.hzcloud.device.service.impl;

import java.util.List;

import com.hzcloud.common.annotation.DataScope;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.utils.DateUtils;
import com.hzcloud.device.mapper.AcsDeviceConMapper;
import com.hzcloud.device.vo.ControllerAndDoorTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.device.mapper.AcsDeviceDoorMapper;
import com.hzcloud.device.domain.AcsDeviceDoor;
import com.hzcloud.device.service.IAcsDeviceDoorService;
import com.hzcloud.common.exception.CustomException;
import com.hzcloud.group.mapper.AscDoorGroupDoorMapper;

/**
 * 门禁门Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
@Service
public class AcsDeviceDoorServiceImpl implements IAcsDeviceDoorService 
{
    @Autowired
    private AcsDeviceDoorMapper acsDeviceDoorMapper;
    @Autowired
    private AcsDeviceConMapper acsDeviceConMapper;

    @Autowired
    private AscDoorGroupDoorMapper ascDoorGroupDoorMapper;

    /**
     * 查询门禁门
     * 
     * @param doorId 门禁门ID
     * @return 门禁门
     */
    @Override
    public AcsDeviceDoor selectAcsDeviceDoorById(Long doorId)
    {
        return acsDeviceDoorMapper.selectAcsDeviceDoorById(doorId);
    }

    @Override
    public List<AcsDeviceDoor> selectAcsDeviceDoorByIds(Long[] doorIds) {
        return acsDeviceDoorMapper.selectAcsDeviceDoorByIds(doorIds);
    }

    @Override
    public List<AcsDeviceDoor> selectAcsDeviceDoorByControllerIds(Long[] controllerIds){
        return acsDeviceDoorMapper.selectAcsDeviceDoorByControllerIds(controllerIds);
    }

    /**
     * 查询门禁门列表
     * 
     * @param acsDeviceDoor 门禁门
     * @return 门禁门
     */
    @Override
    public List<AcsDeviceDoor> selectAcsDeviceDoorList(AcsDeviceDoor acsDeviceDoor)
    {
        return acsDeviceDoorMapper.selectAcsDeviceDoorList(acsDeviceDoor);
    }

    /**
     * 查询门禁门列表过滤
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<AcsDeviceDoor> selectAcsDeviceDoorListDataFilter(AcsDeviceDoor acsDeviceDoor){
        return acsDeviceDoorMapper.selectAcsDeviceDoorListDataFilter(acsDeviceDoor);
    }

    /**
     * 新增门禁门
     * 
     * @param acsDeviceDoor 门禁门
     * @return 结果
     */
    @Override
    public int insertAcsDeviceDoor(AcsDeviceDoor acsDeviceDoor)
    {
        acsDeviceDoor.setCreateTime(DateUtils.getNowDate());
        return acsDeviceDoorMapper.insertAcsDeviceDoor(acsDeviceDoor);
    }

    /**
     * 修改门禁门
     * 
     * @param acsDeviceDoor 门禁门
     * @return 结果
     */
    @Override
    public int updateAcsDeviceDoor(AcsDeviceDoor acsDeviceDoor)
    {
        acsDeviceDoor.setUpdateTime(DateUtils.getNowDate());
        return acsDeviceDoorMapper.updateAcsDeviceDoor(acsDeviceDoor);
    }

    /**
     * 批量删除门禁门
     * 
     * @param doorIds 需要删除的门禁门ID
     * @return 结果
     */
    @Override
    public int deleteAcsDeviceDoorByIds(Long[] doorIds)
    {
        for (Long doorId : doorIds)
        {
            AcsDeviceDoor door = selectAcsDeviceDoorById(doorId);
            if (ascDoorGroupDoorMapper.countDoorGroupDoorByDoorId(doorId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", door.getDoorName()));
            }
        }
        return acsDeviceDoorMapper.deleteAcsDeviceDoorByIds(doorIds);
    }

    /**
     * 删除门禁门信息
     * 
     * @param doorId 门禁门ID
     * @return 结果
     */
    @Override
    public int deleteAcsDeviceDoorById(Long doorId)
    {
        return acsDeviceDoorMapper.deleteAcsDeviceDoorById(doorId);
    }

    /**
     * 获取门禁组中的门数组
     * @param groupId 门禁组ID
     * @return
     */
    @Override
    public List<AcsDeviceDoor> selectListInDoorGroup(Long groupId) {
        return acsDeviceDoorMapper.selectListInDoorGroup(groupId);
    }

    /**
     * 查询不在门禁组中的门　
     * @param acsDeviceDoor
     * @return
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<AcsDeviceDoor> selectListNotInDoorGroup(AcsDeviceDoor acsDeviceDoor) {
        return acsDeviceDoorMapper.selectListNotInDoorGroup(acsDeviceDoor);
    }

    @Override
    public int updateDoorStatus(AcsDeviceDoor acsDeviceDoor) {
        acsDeviceDoor.setUpdateBy("system");
        acsDeviceDoor.setUpdateTime(DateUtils.getNowDate());
        return acsDeviceDoorMapper.updateAcsDeviceDoor(acsDeviceDoor);
    }

    @Override
    public String checkDoorNameUnique(String doorName) {
        int count = acsDeviceDoorMapper.checkDoorNameUnique(doorName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<ControllerAndDoorTreeVo> selectControllerAndDoorTree(Long groupId, String type, Long parentId) {
        List<ControllerAndDoorTreeVo> result = null;
        if ("0".equals(type)) {
            result = acsDeviceConMapper.selectControllerAndDoorTree();
        } else if ("1".equals(type)) {
            result = acsDeviceDoorMapper.selectControllerAndDoorTree(groupId, parentId);
        }
        return result;
    }
}
