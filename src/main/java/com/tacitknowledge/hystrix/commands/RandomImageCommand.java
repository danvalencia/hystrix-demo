package com.tacitknowledge.hystrix.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
public class RandomImageCommand extends HystrixCommand<ImageData> {

    static final HystrixCommandGroupKey GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("RandomImageCommand");

    @Autowired private ImageService imageService;
    @Autowired private ImageData fallbackRandomImage;

    private final String randomKey;

    public RandomImageCommand(final String randomKey) {
        super(Setter.withGroupKey(GROUP_KEY));
        this.randomKey = randomKey;
    }

    @Override
    protected ImageData run()  {
        return imageService.fetchRandomImage(this.randomKey);
    }

    protected ImageData getFallback() {
        return fallbackRandomImage;
    }
}
