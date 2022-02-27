package com.hzcloud.group.service;

import java.util.List;

import com.hzcloud.group.bo.AcsUserGroupEditGroupNameBo;
import com.hzcloud.group.bo.AcsUserGroupEditStatusBo;
import com.hzcloud.group.bo.AcsUserGroupUpdateUserBo;
import com.hzcloud.group.domain.AscUserGroup;

/**
 * 用户组Service接口
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
public interface IAscUserGroupService 
{
    /**
     * 查询用户组
     * 
     * @param groupId 用户组ID
     * @return 用户组
     */
    public AscUserGroup selectAscUserGroupById(Long groupId);

    /**
     * 查询用户组列表
     * 
     * @param ascUserGroup 用户组
     * @return 用户组集合
     */
    public List<AscUserGroup> selectAscUserGroupList(AscUserGroup ascUserGroup);

    /**
     * 新增用户组
     * 
     * @param ascUserGroup 用户组
     * @return 结果
     */
    public int insertAscUserGroup(AscUserGroup ascUserGroup);

    /**
     * 修改用户组
     * 
     * @param ascUserGroup 用户组
     * @return 结果
     */
    public int updateAscUserGroup(AscUserGroup ascUserGroup);

    /**
     * 批量删除用户组
     * 
     * @param groupIds 需要删除的用户组ID
     * @return 结果
     */
    public int deleteAscUserGroupByIds(Long[] groupIds);

    /**
     * 删除用户组信息
     * 
     * @param groupId 用户组ID
     * @return 结果
     */
    public int deleteAscUserGroupById(Long groupId);

    /**
     * 查询用户组中的用户
     * @param groupId
     * @return
     */
    List<Long> selectUserIdsByGroupId(Long groupId);

    /**
     * 修改用户组状态
     * @param bo
     * @return
     */
    int editStatus(AcsUserGroupEditStatusBo bo);

    /**
     * 修改用户组名称
     * @param bo
     * @return
     */
    int editGroupName(AcsUserGroupEditGroupNameBo bo);

    /**
     * 更新用户组内用户
     * @param bo
     * @return
     */
    int updateUser(AcsUserGroupUpdateUserBo bo);

    /**
     *  查询不在门禁组中的用户组
     * @param ascUserGroup
     * @return
     */
    List<AscUserGroup> selectListNotInDoorGroup(AscUserGroup ascUserGroup);

    /**
     *  查询在门禁组中的用户组
     * @param ascUserGroup
     * @return
     */
    List<AscUserGroup> selectListInDoorGroup(AscUserGroup ascUserGroup);

    /**
     * 校验用户组名称是否唯一
     * 
     * @param groupName 用户组名称
     * @return 结果
     */
    public String checkUserGroupNameUnique(String groupName);
}
