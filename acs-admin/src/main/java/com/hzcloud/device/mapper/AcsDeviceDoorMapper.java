package com.hzcloud.device.mapper;

import java.util.List;

import com.hzcloud.device.domain.AcsDeviceDoor;
import com.hzcloud.device.vo.ControllerAndDoorTreeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 门禁门Mapper接口
 *
 * @author zhangfan
 * @date 2021-04-26
 */
@Repository
public interface AcsDeviceDoorMapper {
    /**
     * 查询门禁门
     *
     * @param doorId 门禁门ID
     * @return 门禁门
     */
    AcsDeviceDoor selectAcsDeviceDoorById(Long doorId);

    /**
     * 查询门
     * @param doorIds 门ID数组
     * @return
     */
    List<AcsDeviceDoor> selectAcsDeviceDoorByIds(Long[] doorIds);

    /**
     * 查询门
     * @param controllerIds 控制器ID数组
     * @return
     */
    List<AcsDeviceDoor> selectAcsDeviceDoorByControllerIds(Long[] controllerIds);

    /**
     * 查询门禁门列表
     *
     * @param acsDeviceDoor 门禁门
     * @return 门禁门集合
     */
    List<AcsDeviceDoor> selectAcsDeviceDoorList(AcsDeviceDoor acsDeviceDoor);

    /**
     * 查询门禁门列表过滤
     * @param acsDeviceDoor
     * @return
     */
    List<AcsDeviceDoor> selectAcsDeviceDoorListDataFilter(AcsDeviceDoor acsDeviceDoor);

    /**
     * 新增门禁门
     *
     * @param acsDeviceDoor 门禁门
     * @return 结果
     */
    int insertAcsDeviceDoor(AcsDeviceDoor acsDeviceDoor);

    /**
     * 修改门禁门
     *
     * @param acsDeviceDoor 门禁门
     * @return 结果
     */
    int updateAcsDeviceDoor(AcsDeviceDoor acsDeviceDoor);

    /**
     * 删除门禁门
     *
     * @param doorId 门禁门ID
     * @return 结果
     */
    int deleteAcsDeviceDoorById(Long doorId);

    /**
     * 批量删除门禁门
     *
     * @param doorIds 需要删除的数据ID
     * @return 结果
     */
    int deleteAcsDeviceDoorByIds(Long[] doorIds);

    /**
     * 查询在门禁组中的门，根据门禁组id查询
     *
     * @param groupId
     * @return
     */
    List<AcsDeviceDoor> selectListInDoorGroup(Long groupId);

    /**
     * 根据controllerId和doorPin查询门名称
     *
     * @param controllerId
     * @param pin
     * @return
     */
    String getDoorNameByControllerIdAndPin(@Param("controllerId") Long controllerId, @Param("pin") Integer pin);

    /**
     * 查询不在门禁组中的门
     *
     * @param acsDeviceDoor 门禁组ID
     * @return
     */
    List<AcsDeviceDoor> selectListNotInDoorGroup(AcsDeviceDoor acsDeviceDoor);

    /**
     * 校验门名称是否唯一
     * 
     * @param doorName 门名称
     * @return 结果
     */
    public int checkDoorNameUnique(String doorName);

    /**
     * 通过门禁组id和控制器id获取门列表
     * @param doorGroupId
     * @param controllerId
     * @return
     */
    @Select("select d.door_id as doorId, d.door_name as doorNamr, d.controller_id as controllerId, d.controller_name as controllerName, d.pin, d.status, d.identifier, d.del_flag as delFlag, d.create_by as createBy, d.create_time as createTime, d.update_by as updateBy, d.update_time as updateTime, d.remark from acs_device_door d LEFT JOIN asc_door_group_door dgd ON dgd.door_id = d.door_id WHERE d.controller_id = #{controllerId} AND dgd.door_group_id = #{doorGroupId} And d.del_flag = '0'")
    public List<AcsDeviceDoor> selectDoorsInDoorGroupByControllerId(@Param("doorGroupId") Long doorGroupId, @Param("controllerId") Long controllerId);

    /**
     * 查询门禁控制器，实体为ControllerAndDoorTreeVo，在表格树中使用
     * @return
     */
    @Select("<script>" +
            "select a.door_id as id, a.door_name as name,a.status as status, 1 as type, 0 as hasChildren from acs_device_door a\n" +
            "left join acs_device_controller b on a.controller_id=b.controller_id\n" +
            "<where>\n" +
            "a.del_flag = '0'\n" +
            "<if test=\"groupId != null\">and not exists (select 1 from asc_door_group_door b where b.door_id=a.door_id and b.door_group_id=#{groupId})</if>\n" +
            "<if test=\"controllerId != null and controllerId != ''\"> and a.controller_id=#{controllerId}</if>\n" +
            "</where>" +
            "</script>")
    List<ControllerAndDoorTreeVo> selectControllerAndDoorTree(@Param("groupId") Long groupId, @Param("controllerId") Long controllerId);

     /*
     * 通过控制器id获取门列表数
     * @param controllerId
     * @return
     */
    @Select("select count(1) from acs_device_door where controller_id = #{controllerId}")
    public Integer countDoorByControllerId(Long controllerId);

    /**
     * 根据卡ID获取用户所在的所有门列表
     * @param cardId
     * @return
     */
    @Select("<script>select DISTINCT door_id from acs_device_door  where exists (select 1 from asc_user_group_user a\n" +
    "left join asc_door_group_user_group b on a.user_group_id=b.user_group_id\n" +
    "left join asc_door_group_door c on b.door_group_id=c.door_group_id\n" +
    "left join acs_card d on d.user_id=a.user_id  where d.card_id=#{cardId})</script>")
    public List<Long> selectDoorListByCardId(@Param("cardId") Long cardId);

}
