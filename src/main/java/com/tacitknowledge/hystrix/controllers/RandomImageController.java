package com.tacitknowledge.hystrix.controllers;

import com.tacitknowledge.hystrix.commands.HystrixCommandWrapper;
import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Controller
public class RandomImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping("/random")
    @ResponseBody
    public String randomImage(@RequestParam("tag") String tag) {
        HystrixCommandWrapper<ImageData> randomImageCommand = randomImageCommand();
        System.out.println(String.format("Random Image Command is %s", randomImageCommand));
        final ImageData randomImage = randomImageCommand.run(() -> imageService.fetchRandomImage(tag));
        return String.format("<img src='%s'></img>", randomImage.url);
    }

    @Lookup
    public HystrixCommandWrapper<ImageData> randomImageCommand() {
        throw new UnsupportedOperationException("This method will be overriden by Spring lookup method");
    }
}
