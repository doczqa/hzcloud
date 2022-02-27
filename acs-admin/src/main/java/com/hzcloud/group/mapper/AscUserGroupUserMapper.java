package com.hzcloud.group.mapper;

import java.util.List;

import com.hzcloud.group.domain.AscDoorGroupDoor;
import com.hzcloud.group.domain.AscUserGroupUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户组和用户关联Mapper接口
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@Repository
public interface AscUserGroupUserMapper 
{
    /**
     * 查询用户组和用户关联
     * 
     * @param userGroupId 用户组和用户关联ID
     * @return 用户组和用户关联
     */
    List<AscUserGroupUser> selectAscUserGroupUserById(Long userGroupId);

    /**
     * 查询用户组和用户关联列表
     * 
     * @param ascUserGroupUser 用户组和用户关联
     * @return 用户组和用户关联集合
     */
    List<AscUserGroupUser> selectAscUserGroupUserList(AscUserGroupUser ascUserGroupUser);

    /**
     * 新增用户组和用户关联
     * 
     * @param ascUserGroupUser 用户组和用户关联
     * @return 结果
     */
    int insertAscUserGroupUser(AscUserGroupUser ascUserGroupUser);

    /**
     * 修改用户组和用户关联
     * 
     * @param ascUserGroupUser 用户组和用户关联
     * @return 结果
     */
    int updateAscUserGroupUser(AscUserGroupUser ascUserGroupUser);

    /**
     * 删除用户组和用户关联
     * 
     * @param userGroupId 用户组和用户关联ID
     * @return 结果
     */
    int deleteAscUserGroupUserById(Long userGroupId);

    /**
     * 批量删除用户组和用户关联
     * 
     * @param userGroupIds 需要删除的数据ID
     * @return 结果
     */
    int deleteAscUserGroupUserByIds(Long[] userGroupIds);

    /**
     * 批量添加
     * @param list
     * @return
     */
    @Insert("<script>insert into asc_user_group_user(user_group_id, user_id) values" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">" +
            "(#{groupId},#{item})" +
            "</foreach></script>")
    int batchAdd(@Param("groupId") Long groupId, @Param("list") Long[] list);

    /**
     * 批量移除
     * @param groupId
     * @param list
     * @return
     */
    @Delete("<script>delete from asc_user_group_user where user_group_id = #{groupId} and user_id in" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" close=\")\" separator=\",\">" +
            "#{item}" +
            "</foreach></script>")
    int batchRemove(@Param("groupId") Long groupId, @Param("list") Long[] list);

}
