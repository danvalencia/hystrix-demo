package com.tacitknowledge.hystrix.commands;

import com.netflix.hystrix.*;
import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FetchRandomImageCommand extends HystrixCommand<ImageData> {

    @Autowired private ImageService imageService;

    private String tag;

    public FetchRandomImageCommand() {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FetchRandomImageCommand"))
                                            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("FetchRandomImageThreadPool"))
                                            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                                    .withExecutionTimeoutInMilliseconds(3000))
                                            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                                                    .withMaxQueueSize(10)));
    }

    public ImageData execute(String searchTerm) {
        this.tag = searchTerm;
        return super.execute();
    }

    @Override
    protected ImageData run() {
        return imageService.fetchRandomImage(this.tag);
    }

    @Override
    protected ImageData getFallback() {
        final ImageData imageData = new ImageData();
        imageData.setUrl("http://media1.giphy.com/media/vCKC987OpQAco/100w.gif");
        imageData.setAlt("You Are Awesome");

        return imageData;
    }
}
