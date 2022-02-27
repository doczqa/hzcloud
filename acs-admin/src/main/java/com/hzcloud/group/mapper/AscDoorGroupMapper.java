package com.hzcloud.group.mapper;

import java.util.List;
import com.hzcloud.group.domain.AscDoorGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 门禁组Mapper接口
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@Repository
public interface AscDoorGroupMapper 
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
     * 删除门禁组
     * 
     * @param groupId 门禁组ID
     * @return 结果
     */
    public int deleteAscDoorGroupById(Long groupId);

    /**
     * 批量删除门禁组
     * 
     * @param groupIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAscDoorGroupByIds(Long[] groupIds);

    /**
     * 判断组名是否存在
     * @param groupName
     * @return
     */
    @Select("<script>" +
            "select count(1) from asc_door_group where group_name = #{groupName}" +
            "<if test='groupId != null'>and group_id &lt;&gt; #{groupId}</if>" +
            "</script>")
    int existsByGroupName(@Param("groupName") String groupName, @Param("groupId") Long groupId);

    @Select("SELECT distinct dg.group_id from asc_door_group dg LEFT JOIN asc_door_group_door dgd ON dg.group_id = dgd.door_group_id LEFT JOIN acs_device_door dd ON dd.door_id = dgd.door_id where dd.controller_id = #{controllerId}")
    List<Long> selectGroupIdsByControllerId(@Param("controllerId") Long controllerId);
}
