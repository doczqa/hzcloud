package com.hzcloud.device.service.impl;

import java.util.List;

import com.hzcloud.common.annotation.DataScope;
import com.hzcloud.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hzcloud.device.mapper.AcsCommandQueueMapper;
import com.hzcloud.device.domain.AcsCommandQueue;
import com.hzcloud.device.service.IAcsCommandQueueService;

/**
 * 命令任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-05-31
 */
@Service
public class AcsCommandQueueServiceImpl implements IAcsCommandQueueService 
{
    @Autowired
    private AcsCommandQueueMapper acsCommandQueueMapper;

    /**
     * 查询命令任务
     * 
     * @param taskId 命令任务ID
     * @return 命令任务
     */
    @Override
    public AcsCommandQueue selectAcsCommandQueueById(Long taskId)
    {
        return acsCommandQueueMapper.selectAcsCommandQueueById(taskId);
    }

    /**
     * 查询命令任务列表
     * 
     * @param acsCommandQueue 命令任务
     * @return 命令任务
     */
    @Override
    public List<AcsCommandQueue> selectAcsCommandQueueList(AcsCommandQueue acsCommandQueue)
    {
        return acsCommandQueueMapper.selectAcsCommandQueueList(acsCommandQueue);
    }

    /**
     * 查询命令任务列表过滤
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<AcsCommandQueue> selectAcsCommandQueueListDataFilter(AcsCommandQueue acsCommandQueue){
        return acsCommandQueueMapper.selectAcsCommandQueueListDataFilter(acsCommandQueue);
    }

    /**
     * 新增命令任务
     * 
     * @param acsCommandQueue 命令任务
     * @return 结果
     */
    @Override
    public int insertAcsCommandQueue(AcsCommandQueue acsCommandQueue)
    {
        acsCommandQueue.setCreateTime(DateUtils.getNowDate());
        return acsCommandQueueMapper.insertAcsCommandQueue(acsCommandQueue);
    }

    /**
     * 修改命令任务
     * 
     * @param acsCommandQueue 命令任务
     * @return 结果
     */
    @Override
    public int updateAcsCommandQueue(AcsCommandQueue acsCommandQueue)
    {
        return acsCommandQueueMapper.updateAcsCommandQueue(acsCommandQueue);
    }

    /**
     * 批量删除命令任务
     * 
     * @param taskIds 需要删除的命令任务ID
     * @return 结果
     */
    @Override
    public int deleteAcsCommandQueueByIds(Long[] taskIds)
    {
        return acsCommandQueueMapper.deleteAcsCommandQueueByIds(taskIds);
    }

    /**
     * 删除命令任务信息
     * 
     * @param taskId 命令任务ID
     * @return 结果
     */
    @Override
    public int deleteAcsCommandQueueById(Long taskId)
    {
        return acsCommandQueueMapper.deleteAcsCommandQueueById(taskId);
    }

    @Override
    public List<AcsCommandQueue> selectAcsCommandQueueListByCmd(Long controllerId, Integer[] cmds) {
        return acsCommandQueueMapper.selectAcsCommandQueueListByCmd(controllerId,cmds);
    }

    @Override
    public int updateAcsCommandQueueResult(Long taskId, String resultCode, String resultMsg) {
        return acsCommandQueueMapper.updateAcsCommandQueueResult(taskId, resultCode, resultMsg);
    }
}
