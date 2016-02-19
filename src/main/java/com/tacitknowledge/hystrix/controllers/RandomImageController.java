package com.tacitknowledge.hystrix.controllers;

import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Controller
public class RandomImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping("/")
    @ResponseBody
    public String randomImage() {
        final ImageData randomImage = imageService.fetchRandomImage("test");
        return randomImage.url;
    }
}
