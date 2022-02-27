package com.hzcloud.card.service;

import java.util.List;

import com.hzcloud.card.bo.AcsUserImportInfo;
import com.hzcloud.card.domain.AcsCard;
import com.hzcloud.common.core.domain.entity.SysUser;

/**
 * 卡片管理Service接口
 * 
 * @author zhangfan
 * @date 2021-04-18
 */
public interface IAcsCardService 
{
    /**
     * 查询卡片管理
     * 
     * @param cardId 卡片管理ID
     * @return 卡片管理
     */
    public AcsCard selectAcsCardById(Long cardId);

    /**
     * 查询卡片管理列表
     * 
     * @param acsCard 卡片管理
     * @return 卡片管理集合
     */
    public List<AcsCard> selectAcsCardList(AcsCard acsCard);

    /**
     * 新增卡片管理
     * 
     * @param acsCard 卡片管理
     * @return 结果
     */
    public int insertAcsCard(AcsCard acsCard);
    
    /**
     * 注销卡片管理信息
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    public int cancelAcsCardById(Long id);

    /**
     * 批量注销卡片管理
     * 
     * @param ids 需要注销的卡片管理ID
     * @return 结果
     */
    public int cancelAcsCardByIds(Long[] ids,  String operName);

    /**
     * 冻结卡片管理信息
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    public int frozenAcsCardById(Long id);

    /**
     * 批量冻结卡片管理
     * 
     * @param ids 需要冻结的卡片管理ID
     * @return 结果
     */
    public int frozenAcsCardByIds(Long[] ids);

    /**
     * 解冻卡片管理信息
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    public int unfreezeAcsCardById(Long id);

    /**
     * 批量解冻卡片管理
     * 
     * @param ids 需要解冻的卡片管理ID
     * @return 结果
     */
    public int unfreezeAcsCardByIds(Long[] ids);

    /**
     * 导入卡片数据
     * 
     * @param cardList 卡片数据列表
     * @param operName 操作用户
     * @return 结果
     */
    public String importCard(List<AcsCard> cardList,  String operName);

    /**
     * 导入卡片数据
     * 
     * @param userList 用户数据列表
     * @param operName 操作用户
     * @return 结果
     */
    public String importCardByUser(List<AcsUserImportInfo> userList, String operName);

    /**
     * 通过证件号寻找卡片
     * @param idnumber
     * @return
     */
    public AcsCard selectCardByIDNumber(String idnumber);

    /**
     * 更新卡片
     * @param acsCard
     * @return
     */
    public int updateAcsCard(AcsCard acsCard);

    /**
     * 检查卡片
     */
    public void CheckAcsCard();

    /**
     * 通过卡号来查询卡片
     * @param cardnumber
     * @return
     */
    public AcsCard selectCardByCardNumber(String cardnumber);
}
