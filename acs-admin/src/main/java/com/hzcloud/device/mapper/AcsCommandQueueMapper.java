package com.hzcloud.device.mapper;

import java.util.List;
import com.hzcloud.device.domain.AcsCommandQueue;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 命令任务Mapper接口
 * 
 * @author ruoyi
 * @date 2021-05-31
 */
@Repository
public interface AcsCommandQueueMapper 
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
     * 删除命令任务
     * 
     * @param taskId 命令任务ID
     * @return 结果
     */
    public int deleteAcsCommandQueueById(Long taskId);

    /**
     * 批量删除命令任务
     * 
     * @param taskIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteAcsCommandQueueByIds(Long[] taskIds);

    /**
     * 依控制器id和command集查询任务列表
     */
    @Select("<script>select task_id as taskId, controller_id as controllerId, ip, door_pin as doorPin, command, data, create_time as createTime, issue_time as issueTime, elapsed_time as elapsedTime, status from acs_command_queue where controller_id = #{controllerId} and (status = 1 or ( status = 0 and time_to_sec(timediff(NOW(), issue_time)) > 600 )) and command in" +
            "<foreach item=\"item\" index=\"index\" collection=\"cmds\" open=\"(\" close=\")\" separator=\",\">" +
            "#{item}" +
            "</foreach></script>")
    List<AcsCommandQueue> selectAcsCommandQueueListByCmd(@Param("controllerId") Long controllerId, @Param("cmds") Integer[] cmds);

    /**
     * 更新执行结果
     * @param taskId
     * @param resultCode
     * @param resultMsg
     * @return
     */
    @Update("<script> update acs_command_queue SET process_time = sysdate(), status = 2, result_code = #{resultCode}, result_msg = #{resultMsg} where task_id = #{taskId}</script>")
    int updateAcsCommandQueueResult(@Param("taskId") Long taskId, @Param("resultCode") String resultCode, @Param("resultMsg") String resultMsg);

    /**
     * 批量插入任务
     * @param list
     * @return
     */
    int batchinsertAcsCommandQueue(List<AcsCommandQueue> list);
}
