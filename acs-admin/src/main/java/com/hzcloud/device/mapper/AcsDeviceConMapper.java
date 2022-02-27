package com.hzcloud.device.mapper;

import java.util.List;
import com.hzcloud.device.domain.AcsDeviceCon;

import com.hzcloud.device.vo.ControllerAndDoorTreeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 门禁控制器Mapper接口
 * 
 * @author 张帆
 * @date 2021-04-26
 */
@Repository
public interface AcsDeviceConMapper 
{
    /**
     * 查询门禁控制器
     * 
     * @param controllerId 门禁控制器ID
     * @return 门禁控制器
     */
    public AcsDeviceCon selectAcsDeviceConById(Long controllerId);

    /**
     * 查询门禁控制器列表
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 门禁控制器集合
     */
    public List<AcsDeviceCon> selectAcsDeviceConList(AcsDeviceCon acsDeviceCon);

    /**
     * 根据区域ID查询门禁控制器列表
     * @param zoneId 区域ID
     * @return
     */
    public List<AcsDeviceCon> selectAcsDeviceConListByZoneId(Long zoneId);

    /**
     * 查询门禁控制器列表过滤
     * @param acsDeviceCon
     * @return
     */
    public List<AcsDeviceCon> selectAcsDeviceConListDataFilter(AcsDeviceCon acsDeviceCon);

    /**
     * 新增门禁控制器
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    public int insertAcsDeviceCon(AcsDeviceCon acsDeviceCon);

    /**
     * 修改门禁控制器
     * 
     * @param acsDeviceCon 门禁控制器
     * @return 结果
     */
    public int updateAcsDeviceCon(AcsDeviceCon acsDeviceCon);

    /**
     * 删除门禁控制器
     * 
     * @param controllerId 门禁控制器ID
     * @return 结果
     */
    public int deleteAcsDeviceConById(Long controllerId);

    /**
     * 批量删除门禁控制器
     * 
     * @param controllerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsDeviceConByIds(Long[] controllerIds);

    /**
     * 根据区域ID获取控制器选择框列表
     * 
     * @param zoneId 区域ID
     * @return 选中控制器ID列表
     */
    public List<Long> selectConListByZoneId(Long zoneId);

    /**
     * 根据IP地址获取控制器
     * 
     * @param ip 控制器IP地址
     * @return 控制器
     */
    public List<AcsDeviceCon> selectConByIp(String ip);


    /**
     * 根据卡ID获取用户所在的所有控制器列表
     * @param cardId
     * @return
     */
    @Select("<script>select DISTINCT controller_id from acs_device_door  where exists (select 1 from asc_user_group_user a\n" +
    "left join asc_door_group_user_group b on a.user_group_id=b.user_group_id\n" +
    "left join asc_door_group_door c on b.door_group_id=c.door_group_id\n" +
    "left join acs_card d on d.user_id=a.user_id  where d.card_id=#{cardId})</script>")
    public List<Long> selectConListByCardId(@Param("cardId") Long cardId);

    /**
     * 校验控制器名称是否唯一
     * 
     * @param controllerName 控制器名称
     * @return 结果
     */
    public int checkControllerNameUnique(String controllerName);

    /**
     * 查询门禁控制器，实体为ControllerAndDoorTreeVo，在表格树中使用
     * @return
     */
    @Select("SELECT\n" +
            "\tadc.controller_id AS id,\n" +
            "\tadc.controller_name AS NAME,\n" +
            "\tadc.status AS status,\n" +
            "\t0 AS type,\n" +
            "CASE\n" +
            "\t\tWHEN EXISTS ( SELECT 1 FROM acs_device_door ad WHERE ad.controller_id = adc.controller_id ) THEN\n" +
            "\t\tTRUE ELSE 0 \n" +
            "\tEND AS hasChildren \n" +
            "FROM\n" +
            "\tacs_device_controller adc")
    List<ControllerAndDoorTreeVo> selectControllerAndDoorTree();
}
