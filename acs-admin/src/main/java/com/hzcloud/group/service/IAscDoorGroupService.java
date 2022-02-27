package com.hzcloud.group.service;

import java.util.List;

import com.hzcloud.device.domain.AcsDeviceDoor;
import com.hzcloud.group.bo.AscDoorGroupAddDoorBo;
import com.hzcloud.group.bo.AscDoorGroupAddUserGroupBo;
import com.hzcloud.group.bo.AscDoorGroupRemoveDoorBo;
import com.hzcloud.group.domain.AscDoorGroup;

/**
 * 门禁组Service接口
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
public interface IAscDoorGroupService 
{
    /**
     * 查询门禁组
     * 
     * @param groupId 门禁组ID
     * @return 门禁组
     */
    public AscDoorGroup selectAscDoorGroupById(Long groupId);

    /**
     * 查询门禁组列表
     * 
     * @param ascDoorGroup 门禁组
     * @return 门禁组集合
     */
    public List<AscDoorGroup> selectAscDoorGroupList(AscDoorGroup ascDoorGroup);

    /**
     * 新增门禁组
     * 
     * @param ascDoorGroup 门禁组
     * @return 结果
     */
    public int insertAscDoorGroup(AscDoorGroup ascDoorGroup);

    /**
     * 修改门禁组
     * 
     * @param ascDoorGroup 门禁组
     * @return 结果
     */
    public int updateAscDoorGroup(AscDoorGroup ascDoorGroup);

    /**
     * 批量删除门禁组
     * 
     * @param groupIds 需要删除的门禁组ID
     * @return 结果
     */
    public int deleteAscDoorGroupByIds(Long[] groupIds);

    /**
     * 删除门禁组信息
     * 
     * @param groupId 门禁组ID
     * @return 结果
     */
    public int deleteAscDoorGroupById(Long groupId);

    /**
     * 门禁组添加门
     * @param doorGroupAddDoorBo
     * @return
     */
    int addDoorForGroup(AscDoorGroupAddDoorBo doorGroupAddDoorBo);

    /**
     * 获取门禁组中的门id数组
     * @param groupId
     * @return
     */
    List<Long> getDoorIdsByGroupId(Long groupId);

    /**
     * 移除门
     * @param doorGroupRemoveDoorBo
     * @return
     */
    int removeDoor(AscDoorGroupRemoveDoorBo doorGroupRemoveDoorBo);

    /**
     * 门禁组添加用户组
     * @param bo
     * @return
     */
    int addUserGroupForGroup(AscDoorGroupAddUserGroupBo bo);

    /**
     * 门禁组移除用户组
     * @param bo
     * @return
     */
    int removeUserGroupForGroup(AscDoorGroupAddUserGroupBo bo);

    /**
     * 获取门禁组中的用户组ID数组
     * @param groupId
     * @return
     */
    List<Long> getUserGroupIdsByGroupId(Long groupId);

}
