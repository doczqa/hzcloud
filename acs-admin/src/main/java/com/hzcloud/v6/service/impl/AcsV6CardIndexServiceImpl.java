package com.hzcloud.v6.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.hzcloud.v6.mapper.AcsV6CardIndexMapper;
import com.hzcloud.v6.domain.AcsV6CardIndex;
import com.hzcloud.v6.service.IAcsV6CardIndexService;

/**
 * 卡片索引Service业务层处理
 * 
 * @author zhangfan
 * @date 2021-07-20
 */
@Slf4j
@Service
public class AcsV6CardIndexServiceImpl implements IAcsV6CardIndexService 
{
    @Autowired
    private AcsV6CardIndexMapper acsV6CardIndexMapper;

    @Autowired
	private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询卡片索引
     * 
     * @param controllerId 卡片索引ID
     * @return 卡片索引
     */
    @Override
    public AcsV6CardIndex selectAcsV6CardIndexById(Long controllerId)
    {
        return acsV6CardIndexMapper.selectAcsV6CardIndexById(controllerId);
    }

    /**
     * 查询卡片索引列表
     * 
     * @param acsV6CardIndex 卡片索引
     * @return 卡片索引
     */
    @Override
    public List<AcsV6CardIndex> selectAcsV6CardIndexList(AcsV6CardIndex acsV6CardIndex)
    {
        return acsV6CardIndexMapper.selectAcsV6CardIndexList(acsV6CardIndex);
    }

    /**
     * 新增卡片索引
     * 
     * @param acsV6CardIndex 卡片索引
     * @return 结果
     */
    @Override
    public int insertAcsV6CardIndex(AcsV6CardIndex acsV6CardIndex)
    {
        return acsV6CardIndexMapper.insertAcsV6CardIndex(acsV6CardIndex);
    }

    /**
     * 修改卡片索引
     * 
     * @param acsV6CardIndex 卡片索引
     * @return 结果
     */
    @Override
    public int updateAcsV6CardIndex(AcsV6CardIndex acsV6CardIndex)
    {
        return acsV6CardIndexMapper.updateAcsV6CardIndex(acsV6CardIndex);
    }

    /**
     * 批量删除卡片索引
     * 
     * @param controllerIds 需要删除的卡片索引ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6CardIndexByIds(Long[] controllerIds)
    {
        return acsV6CardIndexMapper.deleteAcsV6CardIndexByIds(controllerIds);
    }

    /**
     * 删除卡片索引信息
     * 
     * @param controllerId 卡片索引ID
     * @return 结果
     */
    @Override
    public int deleteAcsV6CardIndexById(Long controllerId)
    {
        return acsV6CardIndexMapper.deleteAcsV6CardIndexById(controllerId);
    }

    @Override
    public String getAcsV6CardIndexCardIndex(Long controllerId, Long cardId)
    {
        return acsV6CardIndexMapper.getAcsV6CardIndexCardIndex(controllerId, cardId);
    }

    @Override
    public void batchCardIndex(Long controllerId, Integer start, Integer number) {
        log.info("开始批量插入数据，共计:"+ number);
        List<AcsV6CardIndex> list = new ArrayList<AcsV6CardIndex>();
        for(int i = 0; i!= number;i++){
            AcsV6CardIndex cardIndex =  new AcsV6CardIndex();
            cardIndex.setCardId((long) 0);
            cardIndex.setControllerId(controllerId);
            cardIndex.setCardIndex(String.format("%04d", start+i));
            list.add(cardIndex);
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		AcsV6CardIndexMapper cardIndexMapperNew = sqlSession.getMapper(AcsV6CardIndexMapper.class);
        int batchCount = 10;// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < list.size();) {
            if (batchLastIndex >= list.size()) {
                batchLastIndex = list.size();
                
                cardIndexMapperNew.batchinsertAcsV6CardIndex(list.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                log.info("index:" + index+ " batchLastIndex:" + batchLastIndex);
                break;// 数据插入完毕，退出循环
            } else {
                cardIndexMapperNew.batchinsertAcsV6CardIndex(list.subList(index, batchLastIndex));
                sqlSession.commit();
                //清理缓存，防止溢出
                sqlSession.clearCache();
                log.info("index:" + index+ " batchLastIndex:" + batchLastIndex);
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index +(batchCount-1);
            }
        }
        log.info("结束批量插入数据，共计:"+ number);
    }

    @Override
    public int getCardIndexCountofController(Long controllerId) {
        return acsV6CardIndexMapper.getCardIndexCountofController(controllerId);
    }

    @Override
    public String getAcsV6CardIndexFreeIndex(Long controllerId) {
        return acsV6CardIndexMapper.getAcsV6CardIndexFreeIndex(controllerId);
    }

    @Override
    public int updateAcsV6CardIndexCardId(Long controllerId, Long cardId, String cardIndex) {
        return acsV6CardIndexMapper.updateAcsV6CardIndexCardId(controllerId, cardId, cardIndex);
    }

}
