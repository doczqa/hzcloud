package com.hzcloud.device.service.impl;

import com.hzcloud.common.exception.CustomException;
import com.hzcloud.common.utils.StringUtils;
import com.hzcloud.device.bo.Payload;
import com.hzcloud.device.vo.QueueStatusVo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
public class QueueUtils {

    /**
     * 队列状态机　门禁控制器IP->队列
     */
    private static Map<String, BlockingQueue> queueMap = new ConcurrentHashMap<>();

    /**
     * 门禁控制器ID
     *
     * @param ip 　门禁控制器IP
     * @return 队列
     */
    public static BlockingQueue getQueue(String ip) {
        BlockingQueue queue = queueMap.get(ip);
        if (queue == null) {
            queue = new LinkedBlockingQueue<>();
            queueMap.put(ip, queue);
            return queue;
        }
        return queue;
    }

    /**
     * 移除队列
     *
     * @param ip 　门禁控制器ip
     */
    public static void removeQueue(String ip) {
        queueMap.remove(ip);
    }

    /**
     * 入列　FIFO
     * @param payload
     */
    public static void put(Payload payload) {
        if (payload.getControllerId() == null) {
            log.error("门禁控制器ID不能为空");
        }
        try {
            QueueUtils.getQueue(payload.getIp()).put(payload);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 出列 FIFO
     * @param ip 门禁控制器ip
     * @return
     */
    public static Payload take(String ip) {
        if (StringUtils.isEmpty(ip)) {
            log.error("门禁控制器ID不能为空");
        }
        try {
            Object o = QueueUtils.getQueue(ip).take();
            if (!(o instanceof Payload)) {
                throw new CustomException("门禁控制器队列中数据类型异常");
            }
            return (Payload) o;
        } catch (InterruptedException e) {
            log.error("系统异常:",e);
        }
        return null;
    }

    /**
     * 队列状态
     * @return
     */
    public static List<QueueStatusVo> status() {
        Set<String> keySet = QueueUtils.queueMap.keySet();
        List<QueueStatusVo> list = new ArrayList<>();
        for (String key : keySet) {
            QueueStatusVo queueStatusVo = new QueueStatusVo(key, queueMap.get(key).size());
            list.add(queueStatusVo);
        }
        return list;
    }



}
