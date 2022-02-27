package com.hzcloud.v6.service;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6CardIndex;

/**
 * 卡片索引Service接口
 * 
 * @author zhangfan
 * @date 2021-07-20
 */
public interface IAcsV6CardIndexService 
{
    /**
     * 查询卡片索引
     * 
     * @param controllerId 卡片索引ID
     * @return 卡片索引
     */
    public AcsV6CardIndex selectAcsV6CardIndexById(Long controllerId);

    /**
     * 查询卡片索引列表
     * 
     * @param acsV6CardIndex 卡片索引
     * @return 卡片索引集合
     */
    public List<AcsV6CardIndex> selectAcsV6CardIndexList(AcsV6CardIndex acsV6CardIndex);

    /**
     * 新增卡片索引
     * 
     * @param acsV6CardIndex 卡片索引
     * @return 结果
     */
    public int insertAcsV6CardIndex(AcsV6CardIndex acsV6CardIndex);

    /**
     * 修改卡片索引
     * 
     * @param acsV6CardIndex 卡片索引
     * @return 结果
     */
    public int updateAcsV6CardIndex(AcsV6CardIndex acsV6CardIndex);

    /**
     * 批量删除卡片索引
     * 
     * @param controllerIds 需要删除的卡片索引ID
     * @return 结果
     */
    public int deleteAcsV6CardIndexByIds(Long[] controllerIds);

    /**
     * 删除卡片索引信息
     * 
     * @param controllerId 卡片索引ID
     * @return 结果
     */
    public int deleteAcsV6CardIndexById(Long controllerId);

    /**
     * 获取卡片索引号
     * @param controllerId
     * @param cardId
     * @return
     */

    public String getAcsV6CardIndexCardIndex(Long controllerId, Long cardId);

    /**
     * 获取空闲索引号
     * @param controllerId
     * @return
     */
    public String getAcsV6CardIndexFreeIndex(Long controllerId);

    /**
     * 更新卡号
     * @param controllerId
     * @param cardId
     * @param cardIndex
     * @return
     */
    public int updateAcsV6CardIndexCardId(Long controllerId, Long cardId, String cardIndex);

    /**
     * 批处理插入索引记录
     * @param list
     */
    public void batchCardIndex(Long controllerId, Integer start, Integer number);

    /**
     * 获取索引数
     * @param controllerId
     * @return
     */
    public int getCardIndexCountofController(Long controllerId);
}
