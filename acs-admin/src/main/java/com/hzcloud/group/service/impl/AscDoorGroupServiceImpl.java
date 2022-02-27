package com.hzcloud.group.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.hzcloud.common.annotation.DataScope;
import com.hzcloud.common.enums.QueueStatus;
import com.hzcloud.common.exception.CustomException;
import com.hzcloud.common.utils.DateUtils;
import com.hzcloud.device.bo.IssueUserData;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.domain.AcsDeviceDoor;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.device.mapper.AcsCommandQueueMapper;
import com.hzcloud.device.mapper.AcsDeviceDoorMapper;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.device.service.IAcsDeviceDoorService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.group.bo.AscDoorGroupAddDoorBo;
import com.hzcloud.group.bo.AscDoorGroupAddUserGroupBo;
import com.hzcloud.group.bo.AscDoorGroupRemoveDoorBo;
import com.hzcloud.group.domain.AscDoorGroupDoor;
import com.hzcloud.group.domain.AscDoorGroupUserGroup;
import com.hzcloud.group.mapper.AscDoorGroupDoorMapper;
import com.hzcloud.group.mapper.AscDoorGroupUserGroupMapper;
import com.hzcloud.system.service.ISysUserService;
import com.hzcloud.v6.domain.AcsV6CardIndex;
import com.hzcloud.v6.mapper.AcsV6CardIndexMapper;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.group.mapper.AscDoorGroupMapper;
import com.hzcloud.group.domain.AscDoorGroup;
import com.hzcloud.group.service.IAscDoorGroupService;
import com.hzcloud.system.domain.vo.UserVo;


/**
 * 门禁组Service业务层处理
 * 
 * @author jarrymei
 * @date 2021-04-28
 */
@Service
public class AscDoorGroupServiceImpl implements IAscDoorGroupService 
{
    @Autowired
    private AscDoorGroupMapper ascDoorGroupMapper;

    @Autowired
    private AscDoorGroupDoorMapper ascDoorGroupDoorMapper;

    @Autowired
    private AscDoorGroupUserGroupMapper ascDoorGroupUserGroupMapper;

    @Autowired
    private AcsDeviceDoorMapper acsDeviceDoorMapper;

    @Autowired
    private IAcsDeviceDoorService deviceDoorService;

    @Autowired
    private IAcsDeviceConService deviceConService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;

    @Autowired
    private AcsV6CardIndexMapper acsV6CardIndexMapper;

    @Autowired
	private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询门禁组
     * 
     * @param groupId 门禁组ID
     * @return 门禁组
     */
    @Override
    public AscDoorGroup selectAscDoorGroupById(Long groupId)
    {
        return ascDoorGroupMapper.selectAscDoorGroupById(groupId);
    }

    /**
     * 查询门禁组列表
     * 
     * @param ascDoorGroup 门禁组
     * @return 门禁组
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<AscDoorGroup> selectAscDoorGroupList(AscDoorGroup ascDoorGroup)
    {
        return ascDoorGroupMapper.selectAscDoorGroupList(ascDoorGroup);
    }

    /**
     * 新增门禁组
     * 
     * @param ascDoorGroup 门禁组
     * @return 结果
     */
    @Override
    public int insertAscDoorGroup(AscDoorGroup ascDoorGroup)
    {
        if (this.ascDoorGroupMapper.existsByGroupName(ascDoorGroup.getGroupName(), null) > 0) {
            throw new CustomException("门禁组名称不能重复");
        }
        ascDoorGroup.setCreateTime(DateUtils.getNowDate());
        return ascDoorGroupMapper.insertAscDoorGroup(ascDoorGroup);
    }

    /**
     * 修改门禁组
     * 
     * @param ascDoorGroup 门禁组
     * @return 结果
     */
    @Override
    public int updateAscDoorGroup(AscDoorGroup ascDoorGroup)
    {
        if (this.ascDoorGroupMapper.existsByGroupName(ascDoorGroup.getGroupName(), ascDoorGroup.getGroupId()) > 0) {
            throw new CustomException("节点名称不能重复");
        }

        ascDoorGroup.setUpdateTime(DateUtils.getNowDate());
        return ascDoorGroupMapper.updateAscDoorGroup(ascDoorGroup);
    }

    /**
     * 批量删除门禁组
     * 
     * @param groupIds 需要删除的门禁组ID
     * @return 结果
     */
    @Override
    public int deleteAscDoorGroupByIds(Long[] groupIds)
    {
        for(Long groupId:groupIds){
            AscDoorGroup doorGroup = selectAscDoorGroupById(groupId);
            if (ascDoorGroupDoorMapper.countDoorGroupDoorByGroupId(groupId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", doorGroup.getGroupName()));
            }
        }
        return ascDoorGroupMapper.deleteAscDoorGroupByIds(groupIds);
    }

    /**
     * 删除门禁组信息
     * 
     * @param groupId 门禁组ID
     * @return 结果
     */
    @Override
    public int deleteAscDoorGroupById(Long groupId)
    {
        return ascDoorGroupMapper.deleteAscDoorGroupById(groupId);
    }

    @Override
    public int addDoorForGroup(AscDoorGroupAddDoorBo doorGroupAddDoorBo) {
        int rows = 1;
        //删除关联
        /*ascDoorGroupDoorMapper.deleteAscDoorGroupDoorById(doorGroupAddDoorBo.getGroupId());

        List<AscDoorGroupDoor> list = new ArrayList<>();
        for (Long doorId : doorGroupAddDoorBo.getDoorList()) {
            AscDoorGroupDoor ascDoorGroupDoor = new AscDoorGroupDoor();
            ascDoorGroupDoor.setDoorGroupId(doorGroupAddDoorBo.getGroupId());
            ascDoorGroupDoor.setDoorId(doorId);
            list.add(ascDoorGroupDoor);
        }
        if (list.size() > 0) {
            rows = ascDoorGroupDoorMapper.batchAdd(list);
        }*/
        List<AcsCommandQueue> comlist = new ArrayList<AcsCommandQueue>();
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        List<AscDoorGroupDoor> list = new ArrayList<>();
        for (Long doorId : doorGroupAddDoorBo.getDoorList()) {
            AscDoorGroupDoor ascDoorGroupDoor = new AscDoorGroupDoor();
            ascDoorGroupDoor.setDoorGroupId(doorGroupAddDoorBo.getGroupId());
            ascDoorGroupDoor.setDoorId(doorId);
            list.add(ascDoorGroupDoor);
        }
        if (list.size() > 0) {
            rows = ascDoorGroupDoorMapper.batchAdd(list);
            // 流程:用户增量　查询出门禁组对于的用户组并将用户组内所有用户信息下发或者删除

            AscDoorGroupUserGroup condition = new AscDoorGroupUserGroup();
            condition.setDoorGroupId(doorGroupAddDoorBo.getGroupId());
            // 查询门禁组中是否关联用户组，如果关联了就需要存储队列数据
            List<AscDoorGroupUserGroup> doorGroupUserGroupList = ascDoorGroupUserGroupMapper.selectAscDoorGroupUserGroupList(condition);
            if (doorGroupUserGroupList != null && doorGroupUserGroupList.size() > 0) {
                //　查询门禁组中的用户信息
                List<UserVo> userVoList = userService.selectUserVoInDoorGroupAndUserGroup(doorGroupAddDoorBo.getGroupId());
                // 获取用户id数组
                List<Long> userIds = userVoList.stream().map(userVo -> userVo.getUserId()).collect(Collectors.toList());
                List<Long> cardIds = userVoList.stream().map(userVo -> userVo.getCardId()).collect(Collectors.toList());
                if (userIds != null && userIds.size() > 0) {
                    Map<Long,UserVo> usermap = new HashMap<Long,UserVo>();
                    for(UserVo user : userVoList){
                        if(user.getCardStatus().equals("2")){
                            usermap.put(user.getUserId(), new UserVo());
                        }else{
                            usermap.put(user.getUserId(), user);
                        }
                    }
                    //　查询门列表
                    List<AcsDeviceDoor> doorList = deviceDoorService.selectAcsDeviceDoorByIds(doorGroupAddDoorBo.getDoorList());
                    for (AcsDeviceDoor door : doorList) {
                        AcsDeviceCon controller = deviceConService.selectAcsDeviceConById(door.getControllerId());
                        if(controller == null){
                            continue;
                        }
                        Map<Long,String> cardIdIndexMap = new HashMap<Long,String>();
                        List<AcsV6CardIndex> cardIndexList = acsV6CardIndexMapper.selectCardIndexByCardIds(controller.getControllerId(), cardIds);
                        for(Long cardId : cardIds){
                            cardIdIndexMap.put(cardId,null);
                        }
                        for(AcsV6CardIndex cardIndex : cardIndexList){
                            cardIdIndexMap.put(cardIndex.getCardId(),cardIndex.getCardIndex());
                        }
                        List<Long> nullKeys = new ArrayList<Long>();
                        cardIdIndexMap.entrySet().stream().filter(item->(item.getValue() == null)).forEach(item->nullKeys.add(item.getKey()));
                        List<String> freeCardIndexList = acsV6CardIndexMapper.batchGetFreeCardIndex(controller.getControllerId(), nullKeys.size());
                        List<AcsV6CardIndex> needUpdateList = new ArrayList<AcsV6CardIndex>();
                        for(int i = 0; i != freeCardIndexList.size(); i++) {
                            cardIdIndexMap.put(nullKeys.get(i),freeCardIndexList.get(i));
                            AcsV6CardIndex updateCardIndex = new AcsV6CardIndex();
                            updateCardIndex.setControllerId(controller.getControllerId());
                            updateCardIndex.setCardId(nullKeys.get(i));
                            updateCardIndex.setCardIndex(freeCardIndexList.get(i));
                            needUpdateList.add(updateCardIndex);
                        }
                        if(needUpdateList.size() > 0){
                            acsV6CardIndexMapper.barchUpdate(controller.getControllerId(),needUpdateList);
                        }
                        // 以门为单位存储队列数据
                        for(Long userId : userIds){
                            AcsCommandQueue commandQueue = new AcsCommandQueue();
                           
                            conIdIpMap.put(door.getControllerId(), controller.getIp());
                            commandQueue.setControllerId(door.getControllerId());
                            commandQueue.setIp(controller.getIp());
                            commandQueue.setDoorPin(door.getPin());
                            commandQueue.setCommand(ControllerCommandEnum.ISSUED_USER_INFO.command);
                            commandQueue.setCreateTime(new Date());
                            IssueUserData userData = new IssueUserData();
                            userData.setControllerIndex(controller.getControllerIndex());
                            String cardIndex = cardIdIndexMap.get(usermap.get(userId).getCardId());
                            if(cardIndex == null){                                
                                continue;
                            }
                            userData.setCardIndex(cardIndex);
                            userData.setUserName(usermap.get(userId).getNickName());
                            userData.setUserId(usermap.get(userId).getUserId());
                            userData.setCardNumber(usermap.get(userId).getCardNumber());
                            userData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                            userData.setEndTime(usermap.get(userId).getExpirationEndTime());
                            //userData.setPlanTemplateNumber(doorgroup.getWeekTemplateId().intValue());
                            userData.setEnable(true);
                            userData.setFaceInfo(usermap.get(userId).getFaceInfo());
                            commandQueue.setData(userData.toString());
                            commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                            //commandQueueService.insertAcsCommandQueue(commandQueue);
                            comlist.add(commandQueue);
                            if(usermap.get(userId).getCardStatus().equals("1")||usermap.get(userId).getCardStatus().equals("3")){
                                AcsCommandQueue enablecommandQueue = new AcsCommandQueue();
                                enablecommandQueue.setControllerId(door.getControllerId());
                                enablecommandQueue.setIp(controller.getIp());
                                enablecommandQueue.setCommand(ControllerCommandEnum.ENABLE_USER_INFO.command);
                                IssueUserData enableuserData = new IssueUserData();
                                enableuserData.setControllerIndex(controller.getControllerIndex());
                                enableuserData.setCardIndex(cardIndex);
                                enableuserData.setUserName(usermap.get(userId).getNickName());
                                enableuserData.setUserId(usermap.get(userId).getUserId());
                                enableuserData.setCardNumber(usermap.get(userId).getCardNumber());
                                enableuserData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                                enableuserData.setEndTime(usermap.get(userId).getExpirationEndTime());
                                enableuserData.setFaceInfo(usermap.get(userId).getFaceInfo());
                                enableuserData.setEnable(false);
                                enablecommandQueue.setData(enableuserData.toString());
                                enablecommandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                                comlist.add(enablecommandQueue);
                            }
                            
                        }
                    }
                }
            }
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		AcsCommandQueueMapper commandQueueMapperNew = sqlSession.getMapper(AcsCommandQueueMapper.class);
        int batchCount = 10;// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < comlist.size();) {
            if (batchLastIndex >= comlist.size()) {
                batchLastIndex = comlist.size();
                
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                break;// 数据插入完毕，退出循环
            } else {
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index +(batchCount-1);
            }
        }
        if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{1});
            }
        }
        return rows;
    }

    @Override
    public List<Long> getDoorIdsByGroupId(Long groupId) {
        List<AscDoorGroupDoor> ascDoorGroupDoor = ascDoorGroupDoorMapper.selectAscDoorGroupDoorById(groupId);
        return ascDoorGroupDoor.stream().map(ascDoorGroupDoor1 -> ascDoorGroupDoor1.getDoorId()).collect(Collectors.toList());
    }

    @Override
    public int removeDoor(AscDoorGroupRemoveDoorBo doorGroupRemoveDoorBo) {
        List<AcsCommandQueue> comlist = new ArrayList<AcsCommandQueue>();
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        int rows = 1;
        rows = ascDoorGroupDoorMapper.deleteDoor(doorGroupRemoveDoorBo);
        if (rows > 0) {
            // 查询门信息
            AcsDeviceDoor door = deviceDoorService.selectAcsDeviceDoorById(doorGroupRemoveDoorBo.getDoorId());
            AscDoorGroupDoor queryParam = new AscDoorGroupDoor();
            queryParam.setDoorId(door.getDoorId());
            List<AscDoorGroupDoor> checkIsUnique = ascDoorGroupDoorMapper.selectAscDoorGroupDoorList(queryParam);
            if (checkIsUnique.size() > 1) {
                return rows;
            }
            AcsDeviceCon controller = deviceConService.selectAcsDeviceConById(door.getControllerId());
            if(controller == null){
                return rows;
            }
            // 查询门禁组对对应的用户,并插入减量数据
            List<UserVo> userVoList = userService.selectUserVoInDoorGroupAndUserGroup(doorGroupRemoveDoorBo.getGroupId());
            List<Long> userIds = userVoList.stream().map(userVo -> userVo.getUserId()).collect(Collectors.toList());
            List<Long> cardIds = userVoList.stream().map(userVo -> userVo.getCardId()).collect(Collectors.toList());
            if (userIds != null && userIds.size() > 0) {
                Map<Long,UserVo> usermap = new HashMap<Long,UserVo>();
                for(UserVo user : userVoList){
                    if(user.getCardStatus().equals("2")){
                        usermap.put(user.getUserId(), new UserVo());
                    }else{
                        usermap.put(user.getUserId(), user);
                    }
                }
                Map<Long,String> cardIdIndexMap = new HashMap<Long,String>();
                List<AcsV6CardIndex> cardIndexList = acsV6CardIndexMapper.selectCardIndexByCardIds(door.getControllerId(), cardIds);
                for(Long cardId : cardIds){
                    cardIdIndexMap.put(cardId,null);
                }
                for(AcsV6CardIndex cardIndex : cardIndexList){
                    cardIdIndexMap.put(cardIndex.getCardId(),cardIndex.getCardIndex());
                }
                List<Long> nullKeys = new ArrayList<Long>();
                cardIdIndexMap.entrySet().stream().filter(item->(item.getValue() == null)).forEach(item->nullKeys.add(item.getKey()));
                List<String> freeCardIndexList = acsV6CardIndexMapper.batchGetFreeCardIndex(controller.getControllerId(), nullKeys.size());
                List<AcsV6CardIndex> needUpdateList = new ArrayList<AcsV6CardIndex>();
                for(int i = 0; i != freeCardIndexList.size(); i++) {
                    cardIdIndexMap.put(nullKeys.get(i),freeCardIndexList.get(i));
                    AcsV6CardIndex updateCardIndex = new AcsV6CardIndex();
                    updateCardIndex.setControllerId(controller.getControllerId());
                    updateCardIndex.setCardId(nullKeys.get(i));
                    updateCardIndex.setCardIndex(freeCardIndexList.get(i));
                    needUpdateList.add(updateCardIndex);
                }
                if(needUpdateList.size() > 0){
                    acsV6CardIndexMapper.barchUpdate(controller.getControllerId(),needUpdateList);
                }
                
                for(Long userId : userIds){
                    conIdIpMap.put(controller.getControllerId(),controller.getIp());
                    AcsCommandQueue commandQueue = new AcsCommandQueue();
                    commandQueue.setControllerId(door.getControllerId());
                    commandQueue.setIp(controller.getIp());
                    commandQueue.setDoorPin(door.getPin());
                    commandQueue.setCommand(ControllerCommandEnum.DELETE_USER_INFO.command);
                    commandQueue.setCreateTime(new Date());
                    IssueUserData userData = new IssueUserData();
                    userData.setControllerIndex(controller.getControllerIndex());
                    String cardIndex = cardIdIndexMap.get(usermap.get(userId).getCardId());
                    if(cardIndex == null){
                        continue;
                    }
                    userData.setCardIndex(cardIndex);
                    userData.setUserName(usermap.get(userId).getNickName());
                    userData.setUserId(usermap.get(userId).getUserId());
                    userData.setCardNumber(usermap.get(userId).getCardNumber());
                    userData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                    userData.setEndTime(usermap.get(userId).getExpirationEndTime());
                    //userData.setPlanTemplateNumber(doorgroup.getWeekTemplateId().intValue());
                    userData.setEnable(true);
                    userData.setFaceInfo(usermap.get(userId).getFaceInfo());
                    commandQueue.setData(userData.toString());
                    commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                    comlist.add(commandQueue);
                    //commandQueueService.insertAcsCommandQueue(commandQueue);
                }
            }
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		AcsCommandQueueMapper commandQueueMapperNew = sqlSession.getMapper(AcsCommandQueueMapper.class);
        int batchCount = 10;// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < comlist.size();) {
            if (batchLastIndex >= comlist.size()) {
                batchLastIndex = comlist.size();
                
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                break;// 数据插入完毕，退出循环
            } else {
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index +(batchCount-1);
            }
        }
        if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{3});
            }
        }
        return rows;
    }

    @Override
    public int addUserGroupForGroup(AscDoorGroupAddUserGroupBo bo) {
        int rows = 1;
        List<AcsCommandQueue> comlist = new ArrayList<AcsCommandQueue>();
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        // 移除依賴
//        ascDoorGroupUserGroupMapper.deleteAscDoorGroupUserGroupById(bo.getDoorGroupId());
        List<AscDoorGroupUserGroup> list = new ArrayList<>();
        for (Long userGroupId : bo.getUserGroupIds()) {
            AscDoorGroupUserGroup ascDoorGroupUserGroup = new AscDoorGroupUserGroup(bo.getDoorGroupId(), userGroupId);
            list.add(ascDoorGroupUserGroup);
        }
        if (list.size() > 0) {
            // 批量建立门禁组中的用户组关系
            rows = ascDoorGroupUserGroupMapper.batchAdd(list);

            // 查询门禁组中是否有门存在,如果存在存储对应的用户组内用户信息
            // 查询门禁组中的门列表
            List<AcsDeviceDoor> doorList = acsDeviceDoorMapper.selectListInDoorGroup(bo.getDoorGroupId());
            if (doorList != null && doorList.size() > 0) {
                // 查询门禁组中的用户列表
                List<UserVo> userVoList = userService.selectUserVoInDoorGroupAndUserGroup(bo.getDoorGroupId());
                // 获取用户数组
                List<Long> userIds = userVoList.stream().map(userVo -> userVo.getUserId()).collect(Collectors.toList());
                List<Long> cardIds = userVoList.stream().map(userVo -> userVo.getCardId()).collect(Collectors.toList());
                Map<Long,UserVo> usermap = new HashMap<Long,UserVo>();
                for(UserVo user : userVoList){
                    if(user.getCardStatus().equals("2")){
                        usermap.put(user.getUserId(), new UserVo());
                    }else{
                        usermap.put(user.getUserId(), user);
                    }
                }
                for (AcsDeviceDoor door : doorList) {
                    AcsDeviceCon controller = deviceConService.selectAcsDeviceConById(door.getControllerId());
                    if(controller == null){
                        continue;
                    }
                    Map<Long,String> cardIdIndexMap = new HashMap<Long,String>();
                    List<AcsV6CardIndex> cardIndexList = acsV6CardIndexMapper.selectCardIndexByCardIds(door.getControllerId(), cardIds);
                    for(Long cardId : cardIds){
                        cardIdIndexMap.put(cardId,null);
                    }
                    for(AcsV6CardIndex cardIndex : cardIndexList){
                        cardIdIndexMap.put(cardIndex.getCardId(),cardIndex.getCardIndex());
                    }
                    List<Long> nullKeys = new ArrayList<Long>();
                    cardIdIndexMap.entrySet().stream().filter(item->(item.getValue() == null)).forEach(item->nullKeys.add(item.getKey()));
                    List<String> freeCardIndexList = acsV6CardIndexMapper.batchGetFreeCardIndex(controller.getControllerId(), nullKeys.size());
                    List<AcsV6CardIndex> needUpdateList = new ArrayList<AcsV6CardIndex>();
                    for(int i = 0; i != freeCardIndexList.size(); i++) {
                        cardIdIndexMap.put(nullKeys.get(i),freeCardIndexList.get(i));
                        AcsV6CardIndex updateCardIndex = new AcsV6CardIndex();
                        updateCardIndex.setControllerId(controller.getControllerId());
                        updateCardIndex.setCardId(nullKeys.get(i));
                        updateCardIndex.setCardIndex(freeCardIndexList.get(i));
                        needUpdateList.add(updateCardIndex);
                    }
                    if(needUpdateList.size() > 0){
                        acsV6CardIndexMapper.barchUpdate(controller.getControllerId(),needUpdateList);
                    }
                    for(Long userId : userIds){
                        conIdIpMap.put(controller.getControllerId(),controller.getIp());
                        // 以门为单位存储队列数据
                        AcsCommandQueue commandQueue = new AcsCommandQueue();
                        commandQueue.setControllerId(door.getControllerId());
                        commandQueue.setIp(controller.getIp());
                        commandQueue.setDoorPin(door.getPin());
                        commandQueue.setCommand(ControllerCommandEnum.ISSUED_USER_INFO.command);
                        commandQueue.setCreateTime(new Date());
                        IssueUserData userData = new IssueUserData();
                        userData.setControllerIndex(controller.getControllerIndex());
                        String cardIndex = cardIdIndexMap.get(usermap.get(userId).getCardId());
                        if(cardIndex == null){                           
                            continue;                           
                        }
                        userData.setCardIndex(cardIndex);
                        userData.setUserName(usermap.get(userId).getNickName());
                        userData.setUserId(usermap.get(userId).getUserId());
                        userData.setCardNumber(usermap.get(userId).getCardNumber());
                        userData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                        userData.setEndTime(usermap.get(userId).getExpirationEndTime());
                        //userData.setPlanTemplateNumber(doorgroup.getWeekTemplateId().intValue());
                        userData.setFaceInfo(usermap.get(userId).getFaceInfo());
                        userData.setEnable(true);
                        commandQueue.setData(userData.toString());
                        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                        //commandQueueService.insertAcsCommandQueue(commandQueue);
                        comlist.add(commandQueue);
                        if(usermap.get(userId).getCardStatus().equals("1")||usermap.get(userId).getCardStatus().equals("3")){
                            AcsCommandQueue enablecommandQueue = new AcsCommandQueue();
                            enablecommandQueue.setControllerId(controller.getControllerId());
                            enablecommandQueue.setIp(controller.getIp());
                            enablecommandQueue.setCommand(ControllerCommandEnum.ENABLE_USER_INFO.command);
                            IssueUserData enableuserData = new IssueUserData();
                            enableuserData.setControllerIndex(controller.getControllerIndex());
                            enableuserData.setCardIndex(cardIndex);
                            enableuserData.setUserName(usermap.get(userId).getNickName());
                            enableuserData.setUserId(usermap.get(userId).getUserId());
                            enableuserData.setCardNumber(usermap.get(userId).getCardNumber());
                            enableuserData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                            enableuserData.setEndTime(usermap.get(userId).getExpirationEndTime());
                            enableuserData.setFaceInfo(usermap.get(userId).getFaceInfo());
                            enableuserData.setEnable(false);
                            enablecommandQueue.setData(enableuserData.toString());
                            enablecommandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                            comlist.add(enablecommandQueue);
                        }
                    }
                }
            }
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		AcsCommandQueueMapper commandQueueMapperNew = sqlSession.getMapper(AcsCommandQueueMapper.class);
        int batchCount = 10;// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < comlist.size();) {
            if (batchLastIndex >= comlist.size()) {
                batchLastIndex = comlist.size();
                
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                break;// 数据插入完毕，退出循环
            } else {
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index +(batchCount-1);
            }
        }
        if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{1});
            }
        }
        return rows;
    }

    /**
     * 门禁组移除用户组
     * @param bo
     * @return
     */    @Override
    public int removeUserGroupForGroup(AscDoorGroupAddUserGroupBo bo) {
         int rows = 1;
         List<AcsCommandQueue> comlist = new ArrayList<AcsCommandQueue>();
         Map<Long,String> conIdIpMap = new HashMap<Long,String>();
         if (bo.getUserGroupIds() != null && bo.getUserGroupIds().length > 0) {
             rows = ascDoorGroupUserGroupMapper.batchDelete(bo.getDoorGroupId(), bo.getUserGroupIds());

             // 查询门禁组中是否有门存在,如果存在存储对应的用户组内用户信息
             // 查询门禁组中的门列表
             List<AcsDeviceDoor> doorList = acsDeviceDoorMapper.selectListInDoorGroup(bo.getDoorGroupId());
             if (doorList != null && doorList.size() > 0) {
                 // 查询门禁组中的用户列表
                List<UserVo> userVoList = userService.selectUserVoInDoorGroupAndUserGroup(bo.getDoorGroupId());
                // 获取用户数组
                List<Long> userIds = userVoList.stream().map(userVo -> userVo.getUserId()).collect(Collectors.toList());
                List<Long> cardIds = userVoList.stream().map(userVo -> userVo.getCardId()).collect(Collectors.toList());
                Map<Long,UserVo> usermap = new HashMap<Long,UserVo>();
                for(UserVo user : userVoList){
                    if(user.getCardStatus().equals("2")){
                        usermap.put(user.getUserId(), new UserVo());
                    }else{
                        usermap.put(user.getUserId(), user);
                    }
                }
                 for (AcsDeviceDoor door : doorList) {
                    AcsDeviceCon controller = deviceConService.selectAcsDeviceConById(door.getControllerId());
                    if(controller == null){
                        continue;
                    }
                    Map<Long,String> cardIdIndexMap = new HashMap<Long,String>();
                    List<AcsV6CardIndex> cardIndexList = acsV6CardIndexMapper.selectCardIndexByCardIds(door.getControllerId(), cardIds);
                    for(Long cardId : cardIds){
                        cardIdIndexMap.put(cardId,null);
                    }
                    for(AcsV6CardIndex cardIndex : cardIndexList){
                        cardIdIndexMap.put(cardIndex.getCardId(),cardIndex.getCardIndex());
                    }
                    List<Long> nullKeys = new ArrayList<Long>();
                    cardIdIndexMap.entrySet().stream().filter(item->(item.getValue() == null)).forEach(item->nullKeys.add(item.getKey()));
                    List<String> freeCardIndexList = acsV6CardIndexMapper.batchGetFreeCardIndex(controller.getControllerId(), nullKeys.size());
                    List<AcsV6CardIndex> needUpdateList = new ArrayList<AcsV6CardIndex>();
                    for(int i = 0; i != freeCardIndexList.size(); i++) {
                        cardIdIndexMap.put(nullKeys.get(i),freeCardIndexList.get(i));
                        AcsV6CardIndex updateCardIndex = new AcsV6CardIndex();
                        updateCardIndex.setControllerId(controller.getControllerId());
                        updateCardIndex.setCardId(nullKeys.get(i));
                        updateCardIndex.setCardIndex(freeCardIndexList.get(i));
                        needUpdateList.add(updateCardIndex);
                    }
                    if(needUpdateList.size() > 0){
                        acsV6CardIndexMapper.barchUpdate(controller.getControllerId(),needUpdateList);
                    }
                    for(Long userId : userIds){
                         // 以门为单位存储队列数据
                         
                         conIdIpMap.put(controller.getControllerId(),controller.getIp());
                         AcsCommandQueue commandQueue = new AcsCommandQueue();
                         commandQueue.setControllerId(door.getControllerId());
                         commandQueue.setIp(controller.getIp());
                         commandQueue.setDoorPin(door.getPin());
                         commandQueue.setCommand(ControllerCommandEnum.DELETE_USER_INFO.command);
                         commandQueue.setCreateTime(new Date());
                         IssueUserData userData = new IssueUserData();
                         userData.setControllerIndex(controller.getControllerIndex());
                         String cardIndex = cardIdIndexMap.get(usermap.get(userId).getCardId());
                         if(cardIndex == null){                           
                            continue;                           
                        }
                         userData.setCardIndex(cardIndex);
                         userData.setUserName(usermap.get(userId).getNickName());
                         userData.setUserId(usermap.get(userId).getUserId());
                         userData.setCardNumber(usermap.get(userId).getCardNumber());
                         userData.setBeginTime(usermap.get(userId).getExpirationStartTime());
                         userData.setEndTime(usermap.get(userId).getExpirationEndTime());
                         //userData.setPlanTemplateNumber(doorgroup.getWeekTemplateId().intValue());
                         userData.setFaceInfo(usermap.get(userId).getFaceInfo());
                         userData.setEnable(true);
                         commandQueue.setData(userData.toString());
                         commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                         //commandQueueService.insertAcsCommandQueue(commandQueue);
                         comlist.add(commandQueue);
                     }
                 }
             }
         }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		AcsCommandQueueMapper commandQueueMapperNew = sqlSession.getMapper(AcsCommandQueueMapper.class);
        int batchCount = 10;// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < comlist.size();) {
            if (batchLastIndex >= comlist.size()) {
                batchLastIndex = comlist.size();
                
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                break;// 数据插入完毕，退出循环
            } else {
                commandQueueMapperNew.batchinsertAcsCommandQueue(comlist.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index +(batchCount-1);
            }
        }
         if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{3});
            }
        }
        return rows;
    }

    @Override
    public List<Long> getUserGroupIdsByGroupId(Long groupId) {
        List<AscDoorGroupUserGroup> ascDoorGroupUserGroupList = ascDoorGroupUserGroupMapper.selectAscDoorGroupUserGroupById(groupId);
        return ascDoorGroupUserGroupList.stream().map(group -> group.getUserGroupId()).collect(Collectors.toList());
    }
}
