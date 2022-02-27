package com.hzcloud.device.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.hzcloud.common.annotation.Excel;
import com.hzcloud.common.core.domain.BaseEntity;

/**
 * 命令任务对象 acs_command_queue
 * 
 * @author ruoyi
 * @date 2021-05-31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcsCommandQueue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long taskId;

    /** 控制器ID */
    @Excel(name = "控制器ID")
    private Long controllerId;

    /** 门Pin*/
    private Integer doorPin;

    /**
     * ip
     */
    private String ip;

    /** 命令 */
    @Excel(name = "命令")
    private Integer command;

    /** 暂存数据 */
    @Excel(name = "暂存数据")
    private String data;

    /** 下发时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下发时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date issueTime;

    /** 下发时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "执行时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date processTime;

    /** 耗时 */
    @Excel(name = "耗时")
    private Long elapsedTime;

    /** 状态 0:已执行 1:未执行 */
    @Excel(name = "状态 0:已入栈 1:未入栈 2:已执行")
    private String status;

    /**执行结果 0：成功 1：失败 */
    private String resultCode;

    /**执行结果消息 */
    private String resultMsg;
}
