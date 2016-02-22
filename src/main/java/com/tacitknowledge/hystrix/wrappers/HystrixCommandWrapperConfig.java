package com.tacitknowledge.hystrix.wrappers;

import com.tacitknowledge.hystrix.wrappers.HystrixCommandWrapper;
import com.tacitknowledge.hystrix.models.ImageData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
public class HystrixCommandWrapperConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public HystrixCommandWrapper randomImageCommand() {
        final HystrixCommandWrapper<ImageData> hystrixCommandWrapper = new HystrixCommandWrapper<>();
        hystrixCommandWrapper.setGroupKey("ImageCommand");
        hystrixCommandWrapper.setThreadPoolKey("RandomImageCommandThreadPool");
        hystrixCommandWrapper.setThreadPoolSize(10);
        hystrixCommandWrapper.setExecutionTimeout(3000);

        final ImageData imageData = new ImageData();
        imageData.setUrl("http://i.giphy.com/xT0BKhunZXlEsnpz7q.gif");
        imageData.setAlt("You Are Awesome");
        hystrixCommandWrapper.setFallbackResponse(imageData);

        return hystrixCommandWrapper;
    }
}
