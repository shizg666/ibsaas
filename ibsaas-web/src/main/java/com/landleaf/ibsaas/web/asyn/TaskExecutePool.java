package com.landleaf.ibsaas.web.asyn;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.landleaf.ibsaas.web.tcp.thread.ServerHandlerThreadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
@EnableAsync
public class TaskExecutePool {

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final int KEEPALIVETIME = 60;
    /**
     * 线程池所使用的缓冲队列
     */
    private static final int WORKQUEUE = 5;

    /**
     * 业务多线程处理
     */
    @Bean
    public Executor mjRegisterUserTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(100);
        executor.setThreadNamePrefix("Knight-registerUser-Thread");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean("energyDataToRedisThreadPool")
    public ExecutorService energyDataToRedisThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("energyData-thread-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(2, 3,
                KEEPALIVETIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(WORKQUEUE), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }


    @Bean("lightSceneTimeThreadPool")
    public ExecutorService lightSceneTimeThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("light-thread-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(2, 3,
                KEEPALIVETIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(WORKQUEUE), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        return pool;
    }

    @Bean("serviceTaskExecutor")
    public ThreadPoolTaskExecutor serviceTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(200);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadNamePrefix("service-executor-thread-%d");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return taskExecutor;
    }
}