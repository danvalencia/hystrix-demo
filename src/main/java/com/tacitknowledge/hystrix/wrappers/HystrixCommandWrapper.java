package com.tacitknowledge.hystrix.wrappers;

import com.netflix.hystrix.*;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
public class HystrixCommandWrapper<T> {
    private Integer threadPoolSize;
    private Integer executionTimeout;
    private String threadPoolKey;
    private String groupKey;
    private T fallbackResponse;

    public T run(HystrixAction<T> hystrixAction) {
        HystrixCommand<T> response = buildHystrixCommand(hystrixAction);
        return response.execute();
    }

    private HystrixCommand<T> buildHystrixCommand(final HystrixAction<T> hystrixAction) {
        return new HystrixCommand<T>(buildCommandConfiguration()) {
            @Override
            protected T run() throws Exception {
                return hystrixAction.execute();
            }

            @Override
            protected T getFallback() {
                return fallbackResponse;
            }
        };
    }

    private HystrixCommand.Setter buildCommandConfiguration() {
        return HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(threadPoolKey))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(executionTimeout))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withMaxQueueSize(threadPoolSize));
    }

    public void setExecutionTimeout(Integer executionTimeout) {
        this.executionTimeout = executionTimeout;
    }

    public void setThreadPoolSize(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public void setThreadPoolKey(String threadPoolKey) {
        this.threadPoolKey = threadPoolKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public void setFallbackResponse(T fallbackResponse) {
        this.fallbackResponse = fallbackResponse;
    }

    @FunctionalInterface
    public interface HystrixAction<T> {
        T execute();
    }
}

