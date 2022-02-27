package com.hzcloud.v6.mapper;

import java.util.List;
import com.hzcloud.v6.domain.AcsV6CardIndex;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 卡片索引Mapper接口
 * 
 * @author zhangfan
 * @date 2021-07-20
 */
public interface AcsV6CardIndexMapper 
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
     * 删除卡片索引
     * 
     * @param controllerId 卡片索引ID
     * @return 结果
     */
    public int deleteAcsV6CardIndexById(Long controllerId);

    /**
     * 批量删除卡片索引
     * 
     * @param controllerIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsV6CardIndexByIds(Long[] controllerIds);

    /**
     * 获取卡片对于控制器的索引号
     * @param controllerId
     * @param cardId
     * @return
     */
    @Select("select card_index from acs_v6_card_index where controller_id = #{controllerId} and card_id = #{cardId}")
    public String getAcsV6CardIndexCardIndex(@Param("controllerId") Long controllerId, @Param("cardId") Long cardId);

    /**
     * 获取空闲的索引号
     * @param controllerId
     * @return
     */
    @Select("select card_index from acs_v6_card_index where controller_id = #{controllerId} and card_id = 0 limit 1")
    public String getAcsV6CardIndexFreeIndex(@Param("controllerId") Long controllerId);

    /**
     * 更新卡号
     * @param controllerId
     * @param cardId
     * @param cardIndex
     * @return
     */
    @Update("update acs_v6_card_index set card_id = #{cardId} where controller_id = #{controllerId} and card_index = #{cardIndex}")
    public int updateAcsV6CardIndexCardId(@Param("controllerId") Long controllerId, @Param("cardId") Long cardId,@Param("cardIndex") String cardIndex);
    /**
     * 获取控制器的索引数量
     * @param controllerId
     * @return
     */
    @Select("select count(1) from acs_v6_card_index where controller_id = #{controllerId}")
    public int getCardIndexCountofController(@Param("controllerId")Long controllerId);

    /**
     * 批量插入数据
     * @param acsV6CardIndexs
     * @return
     */
    public int batchinsertAcsV6CardIndex(List<AcsV6CardIndex> acsV6CardIndexs);

    @Select("<script>SELECT card_id AS cardId, card_index AS cardIndex FROM acs_v6_card_index  WHERE controller_id=#{controllerId} and card_id in"+
    "<foreach item=\"item\" index=\"index\" collection=\"list\" open=\"(\" close=\")\" separator=\",\">" +
    "#{item}" +
    "</foreach></script>")
    public List<AcsV6CardIndex> selectCardIndexByCardIds(@Param("controllerId") Long controllerId,@Param("list") List<Long> list);

    @Select("SELECT card_index FROM acs_v6_card_index WHERE controller_id=#{controllerId} and card_id=0 LIMIT #{number}")
    public List<String> batchGetFreeCardIndex(@Param("controllerId") Long controllerId,@Param("number") Integer number);

    @Update("<script>" +
    "<foreach collection=\"list\" item=\"card\" index=\"index\" open=\"\" close=\"\" separator=\";\">" +
    "update acs_v6_card_index set card_id=#{card.cardId} where controller_id=#{card.controllerId} and card_index=#{card.cardIndex}" +
    "</foreach>" +
    "</script>")
    public int batchUpdateCardIndex(@Param("list") List<AcsV6CardIndex> list);

    @Update("<script>" +
    "UPDATE acs_v6_card_index SET card_id = CASE " +
    "<foreach collection=\"list\" item=\"item\" index=\"index\">" +
    "  WHEN card_index = #{item.cardIndex} THEN #{item.cardId} " +
    "</foreach> " +
    "END " +
    "WHERE controller_id = #{controllerId} and card_index IN " +
    "<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">" +
      "#{item.cardIndex}" +
    "</foreach>" +
    "</script>")
    public int barchUpdate(@Param("controllerId") Long controllerId, @Param("list") List<AcsV6CardIndex> list);
}
