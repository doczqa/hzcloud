package com.hzcloud.group.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hzcloud.common.annotation.DataScope;
import com.hzcloud.common.constant.UserConstants;
import com.hzcloud.common.enums.QueueStatus;
import com.hzcloud.common.utils.DateUtils;
import com.hzcloud.device.annotation.AcsDataChange;
import com.hzcloud.device.bo.DeleteUserData;
import com.hzcloud.device.bo.IssueUserData;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.device.mapper.AcsCommandQueueMapper;
import com.hzcloud.device.mapper.AcsDeviceDoorMapper;
import com.hzcloud.device.service.IAcsCommandQueueService;
import com.hzcloud.device.service.IAcsDeviceConService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.group.bo.AcsUserGroupEditGroupNameBo;
import com.hzcloud.group.bo.AcsUserGroupEditStatusBo;
import com.hzcloud.group.bo.AcsUserGroupUpdateUserBo;
import com.hzcloud.group.domain.AscDoorGroupDoor;
import com.hzcloud.group.domain.AscDoorGroupUserGroup;
import com.hzcloud.group.domain.AscUserGroupUser;
import com.hzcloud.group.mapper.AscDoorGroupDoorMapper;
import com.hzcloud.group.mapper.AscDoorGroupMapper;
import com.hzcloud.group.mapper.AscDoorGroupUserGroupMapper;
import com.hzcloud.group.mapper.AscUserGroupUserMapper;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.group.mapper.AscUserGroupMapper;
import com.hzcloud.group.domain.AscUserGroup;
import com.hzcloud.group.service.IAscUserGroupService;
import com.hzcloud.system.domain.vo.UserVo;
import com.hzcloud.system.mapper.SysUserMapper;
import com.hzcloud.v6.domain.AcsV6CardIndex;
import com.hzcloud.v6.mapper.AcsV6CardIndexMapper;
import com.hzcloud.v6.service.IAcsV6CardIndexService;

/**
 * 用户组Service业务层处理
 *
 * @author jarrymei
 * @date 2021-04-28
 */
@Service
public class AscUserGroupServiceImpl implements IAscUserGroupService
{
    @Autowired
    private AscUserGroupMapper ascUserGroupMapper;

    @Autowired
    private AscUserGroupUserMapper userGroupUserMapper;

    @Autowired
    private AscDoorGroupUserGroupMapper doorGroupUserGroupMapper;

    @Autowired
    private AscDoorGroupDoorMapper doorGroupDoorMapper;

    @Autowired
    private IAcsCommandQueueService commandQueueService;

    @Autowired
    private AcsDeviceDoorMapper acsDeviceDoorMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AscDoorGroupMapper ascDoorGroupMapper;

    @Autowired
    private IAcsDeviceConService deviceConService;

    @Autowired
    private IAcsV6CardIndexService acsV6CardIndexService;

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;

    @Autowired
    private AcsV6CardIndexMapper acsV6CardIndexMapper;

    @Autowired
	private SqlSessionFactory sqlSessionFactory;
    /**
     * 查询用户组
     *
     * @param groupId 用户组ID
     * @return 用户组
     */
    @Override
    public AscUserGroup selectAscUserGroupById(Long groupId)
    {
        return ascUserGroupMapper.selectAscUserGroupById(groupId);
    }

    /**
     * 查询用户组列表
     *
     * @param ascUserGroup 用户组
     * @return 用户组
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<AscUserGroup> selectAscUserGroupList(AscUserGroup ascUserGroup)
    {
        return ascUserGroupMapper.selectAscUserGroupList(ascUserGroup);
    }

    /**
     * 新增用户组
     *
     * @param ascUserGroup 用户组
     * @return 结果
     */
    @Override
    public int insertAscUserGroup(AscUserGroup ascUserGroup)
    {
        ascUserGroup.setCreateTime(DateUtils.getNowDate());
        int row = ascUserGroupMapper.insertAscUserGroup(ascUserGroup);
        userGroupUserMapper.batchAdd(ascUserGroup.getGroupId(), ascUserGroup.getUserIds());
        return row;
    }

    /**
     * 修改用户组
     *
     * @param ascUserGroup 用户组
     * @return 结果
     */
    @Override
    public int updateAscUserGroup(AscUserGroup ascUserGroup)
    {
/*        ControllerCommandEnum commandEnum = null;
        try {
            Method method = this.getClass().getMethod("updateAscUserGroup", ascUserGroup.getClass());
            AcsDataChange annotation = method.getAnnotation(AcsDataChange.class);
            commandEnum = annotation.value();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }*/

        ascUserGroup.setUpdateTime(DateUtils.getNowDate());
        // 移除依赖关系
        userGroupUserMapper.deleteAscUserGroupUserById(ascUserGroup.getGroupId());
        if (ascUserGroup.getUserIds() != null && ascUserGroup.getUserIds().length > 0) {
            userGroupUserMapper.batchAdd(ascUserGroup.getGroupId(), ascUserGroup.getUserIds());
        }
        int i = ascUserGroupMapper.updateAscUserGroup(ascUserGroup);
        return i;
    }

    /**
     * 批量删除用户组
     *
     * @param groupIds 需要删除的用户组ID
     * @return 结果
     */
    @Override
    @AcsDataChange(ControllerCommandEnum.DELETE_USER_INFO)
    public int deleteAscUserGroupByIds(Long[] groupIds)
    {
        return ascUserGroupMapper.deleteAscUserGroupByIds(groupIds);
    }

    /**
     * 删除用户组信息
     *
     * @param groupId 用户组ID
     * @return 结果
     */
    @Override
    public int deleteAscUserGroupById(Long groupId)
    {
        return ascUserGroupMapper.deleteAscUserGroupById(groupId);
    }

    /**
     * 查询用户组中的用户ID数组
     * @param groupId
     * @return
     */
    @Override
    public List<Long> selectUserIdsByGroupId(Long groupId) {
        List<AscUserGroupUser> ascUserGroupUserList = userGroupUserMapper.selectAscUserGroupUserById(groupId);
        return ascUserGroupUserList.stream().map(ascUserGroupUser -> ascUserGroupUser.getUserId()).collect(Collectors.toList());
    }

    /**
     * 修改用户组状态
     * @param bo
     * @return
     */
    @Override
    public int editStatus(AcsUserGroupEditStatusBo bo) {
        return ascUserGroupMapper.editStatus(bo);
    }

    /**
     * 修改用户组名称
     * @param bo
     * @return
     */
    @Override
    public int editGroupName(AcsUserGroupEditGroupNameBo bo) {
        return ascUserGroupMapper.editGroupName(bo);
    }

    /**
     * 更新用户组中的用户,更新依赖后,添加或移除用户组中的用户都需要将增量|减量的用户数据记录到队列表中
     * @param bo
     * @return
     */
    @Override
    public int updateUser(AcsUserGroupUpdateUserBo bo) {
        if (bo.getUserIds().length < 1) {
            return 1;
        }
        List<AcsCommandQueue> comlist = new ArrayList<AcsCommandQueue>();
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        Map<Long,UserVo> usermap = new HashMap<Long,UserVo>();
        List<Long> userIds = new ArrayList<Long>();
        for(Long uid: bo.getUserIds()){
            userIds.add(uid);
        }
        List<UserVo> userList = sysUserMapper.selectUserVoByUserIds(userIds);
        for(UserVo user : userList){
            if(user.getCardStatus().equals("2")){
                usermap.put(user.getUserId(), new UserVo());
            }else{
                usermap.put(user.getUserId(), user);
            }
        }
        List<Long> cardIds = userList.stream().map(userVo -> userVo.getCardId()).collect(Collectors.toList());

        AscDoorGroupUserGroup condition = new AscDoorGroupUserGroup();
        condition.setUserGroupId(bo.getGroupId());
        // 判断该用户组是否关联门禁组,如果关联了门禁组就需要存储队列数据
        List<AscDoorGroupUserGroup> doorGroupUserGroupList = doorGroupUserGroupMapper.selectAscDoorGroupUserGroupList(condition);

        int i = 0;
        if (bo.getType() == 1) {    //　移除
            i = userGroupUserMapper.batchRemove(bo.getGroupId(), bo.getUserIds());
            // 记录减量数据
            if (doorGroupUserGroupList != null && doorGroupUserGroupList.size() > 0) {
                for (AscDoorGroupUserGroup doorGroupUserGroup : doorGroupUserGroupList ) {
                    // 查询门禁组中的门信息
                    // TODO 唯一的时候生成命令，不唯一的时候部生成命令只删除数据库内容，以后是否会有问题，***待确定***
                    List<AscDoorGroupDoor> doorGroupDoorList = doorGroupDoorMapper.selectAscDoorGroupDoorById(doorGroupUserGroup.getDoorGroupId());
                    for (AscDoorGroupDoor doorGroupDoor : doorGroupDoorList) {
                        AscDoorGroupDoor queryParam = new AscDoorGroupDoor();
                        queryParam.setDoorId(doorGroupDoor.getDoorId());
                        List<AscDoorGroupDoor> checkIsUnique = doorGroupDoorMapper.selectAscDoorGroupDoorList(queryParam);
                        if (checkIsUnique.size() > 1) {
                            break;
                        }
                        AcsDeviceCon controller = deviceConService.selectAcsDeviceConById(doorGroupDoor.getControllerId());
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
                        for(int j = 0; j != freeCardIndexList.size(); j++) {
                            cardIdIndexMap.put(nullKeys.get(j),freeCardIndexList.get(j));
                            AcsV6CardIndex updateCardIndex = new AcsV6CardIndex();
                            updateCardIndex.setControllerId(controller.getControllerId());
                            updateCardIndex.setCardId(nullKeys.get(j));
                            updateCardIndex.setCardIndex(freeCardIndexList.get(j));
                            needUpdateList.add(updateCardIndex);
                        }
                        if(needUpdateList.size() > 0){
                            acsV6CardIndexMapper.barchUpdate(controller.getControllerId(),needUpdateList);
                        }
                        for(Long userId : bo.getUserIds()){
                            
                            conIdIpMap.put(controller.getControllerId(),controller.getIp());
                            AcsCommandQueue commandQueue = new AcsCommandQueue();
                            commandQueue.setControllerId(doorGroupDoor.getControllerId());
                            commandQueue.setIp(controller.getIp());
                            commandQueue.setDoorPin(acsDeviceDoorMapper.selectAcsDeviceDoorById(doorGroupDoor.getDoorId()).getPin());
                            commandQueue.setCommand(ControllerCommandEnum.DELETE_USER_INFO.command);
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
                            //userData.setPlanTemplateNumber(ascDoorGroupMapper.selectAscDoorGroupById(doorGroupDoor.getDoorGroupId()).getWeekTemplateId().intValue());
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
        } else if (bo.getType() == 2) { //　添加
            i = userGroupUserMapper.batchAdd(bo.getGroupId(), bo.getUserIds());
            // 记录增量数据
            if (doorGroupUserGroupList != null && doorGroupUserGroupList.size() > 0) {
                for (AscDoorGroupUserGroup doorGroupUserGroup : doorGroupUserGroupList ) {
                    // 查询门禁组中的门信息
                    List<AscDoorGroupDoor> doorGroupDoorList = doorGroupDoorMapper.selectAscDoorGroupDoorById(doorGroupUserGroup.getDoorGroupId());
                    for (AscDoorGroupDoor doorGroupDoor : doorGroupDoorList) {
                        AcsDeviceCon controller = deviceConService.selectAcsDeviceConById(doorGroupDoor.getControllerId());
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
                        for(int j = 0; j != freeCardIndexList.size(); j++) {
                            cardIdIndexMap.put(nullKeys.get(j),freeCardIndexList.get(j));
                            AcsV6CardIndex updateCardIndex = new AcsV6CardIndex();
                            updateCardIndex.setControllerId(controller.getControllerId());
                            updateCardIndex.setCardId(nullKeys.get(j));
                            updateCardIndex.setCardIndex(freeCardIndexList.get(j));
                            needUpdateList.add(updateCardIndex);
                        }
                        if(needUpdateList.size() > 0){
                            acsV6CardIndexMapper.barchUpdate(controller.getControllerId(),needUpdateList);
                        }
                        for(Long userId : bo.getUserIds()){
                            conIdIpMap.put(controller.getControllerId(),controller.getIp());
                            AcsCommandQueue commandQueue = new AcsCommandQueue();
                            commandQueue.setControllerId(doorGroupDoor.getControllerId());
                            commandQueue.setIp(controller.getIp());
                            commandQueue.setDoorPin(acsDeviceDoorMapper.selectAcsDeviceDoorById(doorGroupDoor.getDoorId()).getPin());
                            commandQueue.setCommand(ControllerCommandEnum.ISSUED_USER_INFO.command);
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
                            //userData.setPlanTemplateNumber(ascDoorGroupMapper.selectAscDoorGroupById(doorGroupDoor.getDoorGroupId()).getWeekTemplateId().intValue());
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
        return i;
    }

    /**
     *  查询不在门禁组中的用户组
     * @param ascUserGroup
     * @return
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<AscUserGroup> selectListNotInDoorGroup(AscUserGroup ascUserGroup) {
        return ascUserGroupMapper.selectListNotInDoorGroup(ascUserGroup);
    }

    /**
     *  查询在门禁组中的用户组
     * @param ascUserGroup
     * @return
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<AscUserGroup> selectListInDoorGroup(AscUserGroup ascUserGroup) {
        return ascUserGroupMapper.selectListInDoorGroup(ascUserGroup);
    }

    /**
     * 校验用户组名称是否唯一
     * 
     * @param groupName 用户组名称
     * @return 结果
     */
    @Override
    public String checkUserGroupNameUnique(String groupName){
        int count = ascUserGroupMapper.checkUserGroupNameUnique(groupName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
