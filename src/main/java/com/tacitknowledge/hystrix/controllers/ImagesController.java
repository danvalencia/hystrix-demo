package com.tacitknowledge.hystrix.controllers;

import com.tacitknowledge.hystrix.commands.FetchRandomImageCommand;
import com.tacitknowledge.hystrix.commands.SearchImagesCommand;
import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Controller
public class ImagesController {

    @Autowired
    private ImageService imageService;

    @RequestMapping("/random")
    @ResponseBody
    public String randomImage(@RequestParam("tag") String tag) {
        final FetchRandomImageCommand fetchRandomImageCommand = randomImageCommand();
        final ImageData imageData = fetchRandomImageCommand.execute(tag);
        //final Future<ImageData> imageDataFuture = fetchRandomImageCommand.queue();
        return String.format("<img src='%s'></img>", imageData.getUrl());
    }

    @RequestMapping("/search")
    @ResponseBody
    public String searchImages(@RequestParam("q") String searchTerm) {
        final SearchImagesCommand searchImagesCommand = searchImagesCommand();
        final List<ImageData> imageDataList = searchImagesCommand.execute(searchTerm);
//        final Future<List<ImageData>> imageDataListFuture = searchImagesCommand.queue();
        String response = imageDataList.stream().map(imageData -> String.format("<img src='%s'></img>", imageData.getUrl())).collect(Collectors.joining());
        return response;
    }

    @Lookup
    public FetchRandomImageCommand randomImageCommand() {
        throw new UnsupportedOperationException("This method will be overriden by Spring lookup method");
    }

    @Lookup
    public SearchImagesCommand searchImagesCommand() {
        throw new UnsupportedOperationException("This method will be overriden by Spring lookup method");
    }
}
