package com.hzcloud.device.service;

import java.util.List;
import com.hzcloud.device.domain.AcsDeviceDoor;
import com.hzcloud.device.vo.ControllerAndDoorTreeVo;

/**
 * 门禁门Service接口
 * 
 * @author zhangfan
 * @date 2021-04-26
 */
public interface IAcsDeviceDoorService 
{
    /**
     * 查询门禁门
     * 
     * @param doorId 门禁门ID
     * @return 门禁门
     */
    public AcsDeviceDoor selectAcsDeviceDoorById(Long doorId);

    /**
     * 查询门
     * @param doorIds　门ID数组
     * @return
     */
    List<AcsDeviceDoor> selectAcsDeviceDoorByIds(Long[] doorIds);

    /**
     * 
     * @param controllerIds
     * @return
     */
    List<AcsDeviceDoor> selectAcsDeviceDoorByControllerIds(Long[] controllerIds);

    /**
     * 查询门禁门列表
     * 
     * @param acsDeviceDoor 门禁门
     * @return 门禁门集合
     */
    public List<AcsDeviceDoor> selectAcsDeviceDoorList(AcsDeviceDoor acsDeviceDoor);

    /**
     * 查询门禁门列表过滤
    */
    public List<AcsDeviceDoor> selectAcsDeviceDoorListDataFilter(AcsDeviceDoor acsDeviceDoor);

    /**
     * 新增门禁门
     * 
     * @param acsDeviceDoor 门禁门
     * @return 结果
     */
    public int insertAcsDeviceDoor(AcsDeviceDoor acsDeviceDoor);

    /**
     * 修改门禁门
     * 
     * @param acsDeviceDoor 门禁门
     * @return 结果
     */
    public int updateAcsDeviceDoor(AcsDeviceDoor acsDeviceDoor);

    /**
     * 批量删除门禁门
     * 
     * @param doorIds 需要删除的门禁门ID
     * @return 结果
     */
    public int deleteAcsDeviceDoorByIds(Long[] doorIds);

    /**
     * 删除门禁门信息
     * 
     * @param doorId 门禁门ID
     * @return 结果
     */
    public int deleteAcsDeviceDoorById(Long doorId);

    /**
     * 获取门禁组中的门数组
     * @param groupId 门禁组ID
     * @return
     */
    List<AcsDeviceDoor> selectListInDoorGroup(Long groupId);

    /**
     * 查询不在门禁组中的门　
     * @param acsDeviceDoor
     * @return
     */
    List<AcsDeviceDoor> selectListNotInDoorGroup(AcsDeviceDoor acsDeviceDoor);

    /**
     * 更新门状态
     * @param acsDeviceDoor
     * @return
     */
    int updateDoorStatus(AcsDeviceDoor acsDeviceDoor);

    /**
     * 校验门名称是否唯一
     * 
     * @param doorName 门名称
     * @return 结果
     */
    String checkDoorNameUnique(String doorName);

    /**
     * 查询不在门禁组中的控制器与门的树
     * @param groupId 门禁组id
     * @param type  类型 0:控制器 1:门
     * @param parentId 父节点ID
     * @return
     */
    List<ControllerAndDoorTreeVo> selectControllerAndDoorTree(Long groupId, String type, Long parentId);
}
