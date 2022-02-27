package com.hzcloud.device.service;

import java.util.List;
import com.hzcloud.device.domain.AcsCommandQueue;

/**
 * 命令任务Service接口
 * 
 * @author ruoyi
 * @date 2021-05-31
 */
public interface IAcsCommandQueueService 
{
    /**
     * 查询命令任务
     * 
     * @param taskId 命令任务ID
     * @return 命令任务
     */
    public AcsCommandQueue selectAcsCommandQueueById(Long taskId);

    /**
     * 查询命令任务列表
     * 
     * @param acsCommandQueue 命令任务
     * @return 命令任务集合
     */
    public List<AcsCommandQueue> selectAcsCommandQueueList(AcsCommandQueue acsCommandQueue);

    /**
     * 查询命令任务列表过滤
     * @param acsCommandQueue
     * @return
     */
    public List<AcsCommandQueue> selectAcsCommandQueueListDataFilter(AcsCommandQueue acsCommandQueue);

    /**
     * 新增命令任务
     * 
     * @param acsCommandQueue 命令任务
     * @return 结果
     */
    public int insertAcsCommandQueue(AcsCommandQueue acsCommandQueue);

    /**
     * 修改命令任务
     * 
     * @param acsCommandQueue 命令任务
     * @return 结果
     */
    public int updateAcsCommandQueue(AcsCommandQueue acsCommandQueue);

    /**
     * 批量删除命令任务
     * 
     * @param taskIds 需要删除的命令任务ID
     * @return 结果
     */
    public int deleteAcsCommandQueueByIds(Long[] taskIds);

    /**
     * 删除命令任务信息
     * 
     * @param taskId 命令任务ID
     * @return 结果
     */
    public int deleteAcsCommandQueueById(Long taskId);

    /**
     * 根据控制器id和命令集获取任务列表
     * 
     * @param controllerId 控制器ID
     * @param cmds  命令集
     * @return 结果
     */
    public List<AcsCommandQueue> selectAcsCommandQueueListByCmd(Long controllerId, Integer[] cmds);

    /**
     * 更新执行结果
     * @param taskId
     * @param result
     * @return
     */
    public int updateAcsCommandQueueResult(Long taskId, String resultCode, String resultMsg);

    
}
