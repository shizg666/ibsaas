package com.landleaf.ibsaas.client.light.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * 异步任务线程池配置
 * @ClassName AsyncTaskConfig
 **/
@EnableAsync
@Configuration
public class AsyncTaskConfig implements AsyncConfigurer {
    /**
     * 线程池维护线程的最大数量
     */
    private static final int MAXIMUMPOOLSIZE = 10;
    /**
     * 线程池维护线程的最少数量
     */
    private static final int COREPOOLSIZE = 3;

    /**
     * 线程池所使用的缓冲队列
     */
    private static final int WORKQUEUE = 10;
    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final int KEEPALIVETIME = 60;

    @Bean("lightThreadPool")
    public ExecutorService noticeThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("light-thread-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(COREPOOLSIZE, MAXIMUMPOOLSIZE,
                KEEPALIVETIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(WORKQUEUE), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }
}
