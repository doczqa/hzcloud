package com.hzcloud.group.mapper;

import java.util.List;

import com.hzcloud.group.bo.AscDoorGroupRemoveDoorBo;
import com.hzcloud.group.domain.AscDoorGroupDoor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 门禁组和门关联Mapper接口
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@Repository
public interface AscDoorGroupDoorMapper 
{
    /**
     * 查询门禁组和门关联
     * 
     * @param doorGroupId 门禁组和门关联ID
     * @return 门禁组和门关联
     */
    public List<AscDoorGroupDoor> selectAscDoorGroupDoorById(Long doorGroupId);

    /**
     * 查询门禁组和门关联列表
     * 
     * @param ascDoorGroupDoor 门禁组和门关联
     * @return 门禁组和门关联集合
     */
    public List<AscDoorGroupDoor> selectAscDoorGroupDoorList(AscDoorGroupDoor ascDoorGroupDoor);

    /**
     * 新增门禁组和门关联
     * 
     * @param ascDoorGroupDoor 门禁组和门关联
     * @return 结果
     */
    public int insertAscDoorGroupDoor(AscDoorGroupDoor ascDoorGroupDoor);

    /**
     * 修改门禁组和门关联
     * 
     * @param ascDoorGroupDoor 门禁组和门关联
     * @return 结果
     */
    public int updateAscDoorGroupDoor(AscDoorGroupDoor ascDoorGroupDoor);

    /**
     * 删除门禁组和门关联
     * 
     * @param doorGroupId 门禁组和门关联ID
     * @return 结果
     */
    public int deleteAscDoorGroupDoorById(Long doorGroupId);

    /**
     * 批量删除门禁组和门关联
     * 
     * @param doorGroupIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAscDoorGroupDoorByIds(Long[] doorGroupIds);

    /**
     * 批量添加
     * @param list
     * @return
     */
    @Insert("<script>insert into asc_door_group_door(door_group_id, door_id) values" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">" +
            "(#{item.doorGroupId},#{item.doorId})" +
            "</foreach></script>")
    int batchAdd(List<AscDoorGroupDoor> list);

    /**
     * 关联表中删除门
     * @param bo
     * @return
     */
    @Delete("delete from asc_door_group_door where door_group_id=#{groupId} and door_id=#{doorId}")
    int deleteDoor(AscDoorGroupRemoveDoorBo bo);

    /**
     * 批量删除门禁组中的门
     * @param groupId
     * @param list
     * @return
     */
    @Delete("<script>delete from asc_door_group_door where door_group_id = #{groupId} and door_id in" +
            "<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" close=\")\" separator=\",\">" +
            "#{item}" +
            "</foreach></script>")
    int batchDelete(Long groupId, Long[] list);
    
    /**
     * 
     * @param doorId
     * @return
     */
    @Select("select count(1) from asc_door_group_door where door_id = #{doorId}")
    int countDoorGroupDoorByDoorId(Long doorId);

    /**
     * 
     * @param groupId
     * @return
     */
    @Select("select count(1) from asc_door_group_door where door_group_id = #{groupId}")
    int countDoorGroupDoorByGroupId(Long groupId);
}
