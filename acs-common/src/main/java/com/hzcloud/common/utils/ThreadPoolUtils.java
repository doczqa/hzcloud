package com.hzcloud.common.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 单例线程池
 * @author jarrymei
 */
public class ThreadPoolUtils {

    private static final int DEFAULT_CORE_SIZE=30;
    private static final int MAX_QUEUE_SIZE=120;
    private volatile static ThreadPoolExecutor executor;


    public static ThreadPoolExecutor getInstance() {

        if (executor == null) {
            synchronized (ThreadPoolUtils.class) {
                if (executor == null) {
                    executor = new ThreadPoolExecutor(DEFAULT_CORE_SIZE,// 核心线程数
                            MAX_QUEUE_SIZE, // 最大线程数
                            10L, // 闲置线程存活时间
                            TimeUnit.SECONDS,// 时间单位
                            new LinkedBlockingDeque<>(),// 线程队列
                            Executors.defaultThreadFactory(),
                            new ThreadPoolExecutor.AbortPolicy()// 线程工厂
                    );
                }
            }
        }
        return executor;
    }

    public void execute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        executor.execute(runnable);
    }

    // 从线程队列中移除对象
    public void cancel(Runnable runnable) {
        if (executor != null) {
            executor.getQueue().remove(runnable);
        }
    }
}
