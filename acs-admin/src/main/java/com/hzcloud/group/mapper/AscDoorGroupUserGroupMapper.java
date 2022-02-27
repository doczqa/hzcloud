package com.hzcloud.group.mapper;

import java.util.List;

import com.hzcloud.group.domain.AscDoorGroupDoor;
import com.hzcloud.group.domain.AscDoorGroupUserGroup;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户组和用户关联Mapper接口
 * 
 * @author ruoyi
 * @date 2021-04-28
 */
@Repository
public interface AscDoorGroupUserGroupMapper 
{
    /**
     * 查询用户组和用户关联
     * 
     * @param doorGroupId 用户组和用户关联ID
     * @return 用户组和用户关联
     */
    public List<AscDoorGroupUserGroup> selectAscDoorGroupUserGroupById(Long doorGroupId);

    /**
     * 查询用户组和用户关联列表
     * 
     * @param ascDoorGroupUserGroup 用户组和用户关联
     * @return 用户组和用户关联集合
     */
    public List<AscDoorGroupUserGroup> selectAscDoorGroupUserGroupList(AscDoorGroupUserGroup ascDoorGroupUserGroup);

    /**
     * 新增用户组和用户关联
     * 
     * @param ascDoorGroupUserGroup 用户组和用户关联
     * @return 结果
     */
    public int insertAscDoorGroupUserGroup(AscDoorGroupUserGroup ascDoorGroupUserGroup);

    /**
     * 修改用户组和用户关联
     * 
     * @param ascDoorGroupUserGroup 用户组和用户关联
     * @return 结果
     */
    public int updateAscDoorGroupUserGroup(AscDoorGroupUserGroup ascDoorGroupUserGroup);

    /**
     * 删除用户组和用户关联
     * 
     * @param doorGroupId 用户组和用户关联ID
     * @return 结果
     */
    public int deleteAscDoorGroupUserGroupById(Long doorGroupId);

    /**
     * 批量删除用户组和用户关联
     * 
     * @param doorGroupIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAscDoorGroupUserGroupByIds(Long[] doorGroupIds);

    /**
     * 批量添加
     * @param list
     * @return
     */
    @Insert("<script>insert into asc_door_group_user_group(door_group_id, user_group_id) values" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">" +
            "(#{item.doorGroupId},#{item.userGroupId})" +
            "</foreach></script>")
    int batchAdd(List<AscDoorGroupUserGroup> list);

    /**
     * 批量删除门禁组中的用户组
     * @param list
     * @return
     */
    @Insert("<script>delete from asc_door_group_user_group where door_group_id = #{doorGroupId} and user_group_id in" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" close=\")\" separator=\",\">" +
            "#{item}" +
            "</foreach></script>")
    int batchDelete(@Param("doorGroupId") Long doorGroupId, @Param("list") Long[] list);
}
