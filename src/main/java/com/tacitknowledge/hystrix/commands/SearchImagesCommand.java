package com.tacitknowledge.hystrix.commands;

import com.netflix.hystrix.*;
import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SearchImagesCommand extends HystrixCommand<List<ImageData>> {

    @Autowired
    private ImageService imageService;

    private String searchTerm;

    public SearchImagesCommand() {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("SearchImagesCommand"))
                                            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SearchImagesThreadPool"))
                                            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                    .withExecutionTimeoutInMilliseconds(3000)
                                                    .withCircuitBreakerEnabled(true)
                                                    .withCircuitBreakerErrorThresholdPercentage(50))
                                            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                                                    .withCoreSize(10)
                                                    .withMaxQueueSize(5)
                                                    .withQueueSizeRejectionThreshold(10)));
    }

    public List<ImageData> execute(String searchTerm) {
        this.searchTerm = searchTerm;
        return super.execute();
    }

    @Override
    protected List<ImageData> run() {
        return imageService.searchImages(this.searchTerm);
    }

    @Override
    protected List<ImageData> getFallback() {
        final ImageData imageData = new ImageData();
        imageData.setUrl("http://media1.giphy.com/media/vCKC987OpQAco/100w.gif");
        imageData.setAlt("You Are Awesome");

        return Collections.singletonList(imageData);
    }
}
