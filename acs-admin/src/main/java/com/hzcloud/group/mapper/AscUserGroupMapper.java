package com.hzcloud.group.mapper;

import java.util.List;

import com.hzcloud.group.bo.AcsUserGroupEditGroupNameBo;
import com.hzcloud.group.bo.AcsUserGroupEditStatusBo;
import com.hzcloud.group.domain.AscUserGroup;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 用户组Mapper接口
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@Repository
public interface AscUserGroupMapper 
{
    /**
     * 查询用户组
     * 
     * @param groupId 用户组ID
     * @return 用户组
     */
     AscUserGroup selectAscUserGroupById(Long groupId);

    /**
     * 查询用户组列表
     * 
     * @param ascUserGroup 用户组
     * @return 用户组集合
     */
     List<AscUserGroup> selectAscUserGroupList(AscUserGroup ascUserGroup);

    /**
     * 新增用户组
     * 
     * @param ascUserGroup 用户组
     * @return 结果
     */
     int insertAscUserGroup(AscUserGroup ascUserGroup);

    /**
     * 修改用户组
     * 
     * @param ascUserGroup 用户组
     * @return 结果
     */
     int updateAscUserGroup(AscUserGroup ascUserGroup);

    /**
     * 删除用户组
     * 
     * @param groupId 用户组ID
     * @return 结果
     */
     int deleteAscUserGroupById(Long groupId);

    /**
     * 批量删除用户组
     * 
     * @param groupIds 需要删除的数据ID
     * @return 结果
     */
     int deleteAscUserGroupByIds(Long[] groupIds);

    /**
     * 修改用户组状态
     * @param bo
     * @return
     */
    @Update("update asc_user_group set status = #{status} where group_id=#{groupId}")
    int editStatus(AcsUserGroupEditStatusBo bo);

    /**
     * 修改用户组名称
     * @param bo
     * @return
     */
    @Update("update asc_user_group set group_name = #{groupName} where group_id=#{groupId}")
    int editGroupName(AcsUserGroupEditGroupNameBo bo);

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
    public int checkUserGroupNameUnique(String groupName);
}
