package com.tacitknowledge.hystrix.config;

import com.tacitknowledge.hystrix.commands.HystrixCommandWrapper;
import com.tacitknowledge.hystrix.models.ImageData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Configuration
public class CommandConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public HystrixCommandWrapper randomImageCommand() {
        final HystrixCommandWrapper<ImageData> hystrixCommandWrapper = new HystrixCommandWrapper<>();
        hystrixCommandWrapper.setGroupKey("ImageCommand");
        hystrixCommandWrapper.setThreadPoolKey("RandomImageCommandThreadPool");
        hystrixCommandWrapper.setThreadPoolSize(10);
        hystrixCommandWrapper.setExecutionTimeout(3000);

        final ImageData imageData = new ImageData();
        imageData.url = "http://i.giphy.com/xT0BKhunZXlEsnpz7q.gif";
        imageData.alt = "You Are Awesome";
        hystrixCommandWrapper.setFallbackResponse(imageData);

        return hystrixCommandWrapper;
    }
}
