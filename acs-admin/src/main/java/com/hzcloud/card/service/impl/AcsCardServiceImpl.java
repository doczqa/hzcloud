package com.hzcloud.card.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.common.annotation.DataScope;
import com.hzcloud.card.mapper.AcsCardMapper;
import com.hzcloud.card.bo.AcsUserImportInfo;
import com.hzcloud.card.domain.AcsCard;
import com.hzcloud.card.service.IAcsCardService;
import com.hzcloud.common.utils.StringUtils;
import com.hzcloud.device.bo.IssueUserData;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.domain.AcsDeviceCon;
import com.hzcloud.device.enums.ControllerCommandEnum;
import com.hzcloud.device.mapper.AcsDeviceConMapper;
import com.hzcloud.device.service.IAcsCommandQueueService;
import com.hzcloud.device.service.IDeviceControllerQueueService;
import com.hzcloud.system.mapper.SysUserMapper;
import com.hzcloud.v6.service.IAcsV6CardIndexService;
import com.hzcloud.common.core.domain.entity.SysUser;
import com.hzcloud.common.enums.QueueStatus;
import com.hzcloud.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 卡片管理Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-04-18
 */
@Service
public class AcsCardServiceImpl implements IAcsCardService 
{
    private static final Logger log = LoggerFactory.getLogger(AcsCardServiceImpl.class);

    @Autowired
    private AcsCardMapper acsCardMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private IAcsV6CardIndexService acsV6CardIndexService;

    @Autowired
    private IDeviceControllerQueueService deviceControllerQueueService;

    @Autowired
    private AcsDeviceConMapper deviceConMapper;

    @Autowired
    private IAcsCommandQueueService commandQueueService;

    /**
     * 查询卡片管理
     * 
     * @param cardId 卡片管理ID
     * @return 卡片管理
     */
    @Override
    public AcsCard selectAcsCardById(Long cardId)
    {
        return acsCardMapper.selectAcsCardById(cardId);
    }

    /**
     * 查询卡片管理列表
     * 
     * @param acsCard 卡片管理
     * @return 卡片管理
     */
    @Override
    @DataScope(deptAlias = "c")
    public List<AcsCard> selectAcsCardList(AcsCard acsCard)
    {
        return acsCardMapper.selectAcsCardList(acsCard);
    }

    /**
     * 新增卡片管理
     * 
     * @param acsCard 卡片管理
     * @return 结果
     */
    @Override
    public int insertAcsCard(AcsCard acsCard)
    {
        return acsCardMapper.insertAcsCard(acsCard);
    }

    /**
     * 更新卡片
     */
    @Override
    public int updateAcsCard(AcsCard acsCard){
        return acsCardMapper.updateAcsCard(acsCard);
    }

    /**
     * 批量注销卡片管理
     * 
     * @param ids 需要注销的卡片管理ID
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public int cancelAcsCardByIds(Long[] ids,String oper_name)
    {
        return acsCardMapper.cancelAcsCardByIds(ids, oper_name);
    }

    /**
     * 注销卡片管理信息
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    @Override
    public int cancelAcsCardById(Long id)
    {
        return acsCardMapper.cancelAcsCardById(id);
    } 

    /**
     * 批量冻结卡片管理
     * 
     * @param ids 需要冻结的卡片管理ID
     * @return 结果
     */
    @Override
    public int frozenAcsCardByIds(Long[] ids)
    {
        return acsCardMapper.frozenAcsCardByIds(ids);
    }

    /**
     * 冻结卡片管理信息
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    @Override
    public int frozenAcsCardById(Long id)
    {
        return acsCardMapper.frozenAcsCardById(id);
    }

    /**
     * 批量解冻卡片管理
     * 
     * @param ids 需要解冻的卡片管理ID
     * @return 结果
     */
    @Override
    public int unfreezeAcsCardByIds(Long[] ids)
    {
        return acsCardMapper.unfreezeAcsCardByIds(ids);
    }

    /**
     * 解冻卡片管理信息
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    @Override
    public int unfreezeAcsCardById(Long id)
    {
        return acsCardMapper.unfreezeAcsCardById(id);
    }

    /**
     * 导入卡片数据
     * 
     * @param cardList 卡片数据列表
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importCard(List<AcsCard> cardList,  String operName)
    {
        if (StringUtils.isNull(cardList) || cardList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (AcsCard card : cardList)
        {
            try
            {
                // 验证是否存在这个用户
                AcsCard c = acsCardMapper.selectCardByIDNumber(card.getIdNumber());
                if (StringUtils.isNull(c))
                {
                    SysUser u = userMapper.selectUserByIDNumber(card.getIdNumber());
                    if (StringUtils.isNull(u)){
                        u = new SysUser();
                        u.setDeptId(card.getDeptId());
                        u.setUserName(card.getUserName());
                        u.setNickName(card.getUserName());
                        u.setIdNumber(card.getIdNumber());
                        u.setIdType(card.getIdType());
                        u.setSex(card.getSex());
                        u.setAddress(card.getAddress());
                        u.setPhonenumber(card.getPhonenumber());
                        u.setIdentityId(card.getIdentityId());
                        u.setStatus("0");
                        userMapper.insertUser(u);
                        card.setUserId(u.getUserId());
                    }else{
                        card.setUserId(u.getUserId());
                    }
                    card.setCreateBy(operName);
                    card.setExpirationStartTime(new Date());
                    this.insertAcsCard(card);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + card.getUserName() + " 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + card.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + card.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 导入卡片数据
     * 
     * @param userList 卡片数据列表
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importCardByUser(List<AcsUserImportInfo> userList,  String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (AcsUserImportInfo user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                AcsCard c = acsCardMapper.selectCardByIDNumber(user.getIdNumber());
                if (StringUtils.isNull(c))
                {
                    AcsCard card = new AcsCard();
                    card.setUserName(user.getNickName());
                    card.setPhonenumber(user.getPhonenumber());
                    card.setCreateBy(operName);
                    card.setIdType(user.getIdType());
                    card.setIdNumber(user.getIdNumber());
                    card.setIdentityId(user.getIdentityId());
                    card.setAddress(user.getAddress());
                    card.setSex(user.getSex());
                    card.setCardNumber(user.getCardNumber());
                    card.setExpirationStartTime(new Date());
                    card.setExpirationEndTime(user.getExpirationEndTime());
                    this.insertAcsCard(card);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getNickName() + " 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getNickName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getNickName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 通过证件号寻找卡片
     */
    public AcsCard selectCardByIDNumber(String idnumber){
        return acsCardMapper.selectCardByIDNumber(idnumber);
    }

    @Override
    public void CheckAcsCard() {
        Map<Long,String> conIdIpMap = new HashMap<Long,String>();
        List<AcsCard> cardList =  acsCardMapper.selectAcsCardList(null);
        for(AcsCard card: cardList){
            Date endDate = card.getExpirationEndTime();
            if((endDate.compareTo(new Date()) < 0) && (card.getStatus() == "0" || card.getStatus() == "1")){
                if(card.getStatus() == "0"){
                    List<Long> controllerIds  = deviceConMapper.selectConListByCardId(card.getCardId());
                    for(Long controllerId : controllerIds){
                        AcsDeviceCon con = deviceConMapper.selectAcsDeviceConById(controllerId);
                        if(con == null){
                            continue;
                        }
                        conIdIpMap.put(controllerId,con.getIp());
                        AcsCommandQueue commandQueue = new AcsCommandQueue();
                        commandQueue.setControllerId(controllerId);
                        commandQueue.setIp(con.getIp());
                        commandQueue.setCommand(ControllerCommandEnum.ENABLE_USER_INFO.command);
                        IssueUserData userData = new IssueUserData();
                        userData.setControllerIndex(con.getControllerIndex());
                        String cardIndex = acsV6CardIndexService.getAcsV6CardIndexCardIndex(controllerId, card.getCardId());
                        if(cardIndex == null){
                            cardIndex = acsV6CardIndexService.getAcsV6CardIndexFreeIndex(controllerId);
                            if(cardIndex == null){
                                continue;
                            }
                            acsV6CardIndexService.updateAcsV6CardIndexCardId(controllerId, card.getCardId(), cardIndex);
                        }
                        userData.setCardIndex(cardIndex);
                        userData.setEnable(false);
                        commandQueue.setData(userData.toString());
                        commandQueue.setStatus(QueueStatus.NO_EXECUTE.code);
                        commandQueueService.insertAcsCommandQueue(commandQueue);
                    }
                }
                card.setStatus("3");
                acsCardMapper.updateAcsCard(card);
            }
        } 
        if(!conIdIpMap.isEmpty()){
            for (Map.Entry<Long, String> entry : conIdIpMap.entrySet()) {
                deviceControllerQueueService.execute(entry.getKey(),entry.getValue(), new Integer[]{2});
            }
        }
    }

    @Override
    public AcsCard selectCardByCardNumber(String cardnumber) {
        return acsCardMapper.selectCardByCardNumber(cardnumber);
    }
}
