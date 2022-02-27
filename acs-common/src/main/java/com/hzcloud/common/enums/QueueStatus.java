package com.hzcloud.common.enums;

/**
 * 队列执行状态
 */
public enum QueueStatus {

    /**
     * 已入栈
     */
    EXECUTED("0"),
    /**
     * 未入栈
     */
    NO_EXECUTE("1"),
    /**
     * 已执行
     */
    NO_IN_QUEUE("2"),
    ;

    public String code;

    QueueStatus(String code) {
        this.code = code;
    }
}
