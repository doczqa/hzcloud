package com.hzcloud.card.mapper;

import java.util.List;
import com.hzcloud.card.domain.AcsCard;

/**
 * 卡片管理Mapper接口
 * 
 * @author zhangfan
 * @date 2021-04-18
 */
public interface AcsCardMapper 
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
     * 更新卡片管理
     */
    public int updateAcsCard(AcsCard acsCard);

    /**
     * 注销卡片管理
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    public int cancelAcsCardById(Long id);

    /**
     * 批量注销卡片管理
     * 
     * @param ids 需要注销的数据ID
     * @param oper_name
     * @return 结果
     */
    public int cancelAcsCardByIds(Long[] ids, String oper_name);

    /**
     * 冻结卡片管理
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    public int frozenAcsCardById(Long id);

    /**
     * 批量冻结卡片管理
     * 
     * @param ids 需要冻结的数据ID
     * @return 结果
     */
    public int frozenAcsCardByIds(Long[] ids);

    /**
     * 解冻卡片管理
     * 
     * @param id 卡片管理ID
     * @return 结果
     */
    public int unfreezeAcsCardById(Long id);

    /**
     * 批量解冻卡片管理
     * 
     * @param ids 需要解冻的数据ID
     * @return 结果
     */
    public int unfreezeAcsCardByIds(Long[] ids);

    /**
     * 通过身份证号查询卡片
     * 
     * @param idnumber 身份证号
     * @return 卡片对象信息
     */
    public AcsCard selectCardByIDNumber(String idnumber);

    /**
     * 通过卡号查询卡片
     * 
     * @param cardnumber 身份证号
     * @return 卡片对象信息
     */
    public AcsCard selectCardByCardNumber(String cardnumber);
}
