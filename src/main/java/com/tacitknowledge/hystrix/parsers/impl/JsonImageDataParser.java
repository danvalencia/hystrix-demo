package com.tacitknowledge.hystrix.parsers.impl;

import com.google.gson.Gson;
import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.parsers.ImageDataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Component
public class JsonImageDataParser implements ImageDataParser{

    @Autowired
    private Gson gson;

    @Override
    public ImageData fromString(String imageContent) {
        Map<String, Object> imageMap = gson.fromJson(imageContent, Map.class);
        final ImageData imageData = new ImageData();

        if(imageMap != null) {
            final Object data = imageMap.get("data");
            if (data != null) {
                if(data instanceof Map) {
                    Map<String, String> dataMap = (Map<String, String>) data;
                    imageData.setUrl(dataMap.get("image_url"));
                    imageData.setAlt(dataMap.get("caption"));
                }
            }
        }

        return imageData;
    }

    @Override
    public List<ImageData> fromStringToList(String imageContent) {
        Map<String, Object> imageMap = gson.fromJson(imageContent, Map.class);

        List<ImageData> imageDataList = Collections.emptyList();

        if (imageMap != null) {
            final Object data = imageMap.get("data");
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) data;
            imageDataList = dataList.stream().map(img -> {
                final ImageData imageData = new ImageData();
                String caption = (String) img.get("caption");
                imageData.setAlt(caption);
                Object images = img.get("images");
                if (images != null && images instanceof Map) {
                    Map<String, Object> imagesMap = (Map<String, Object>) images;
                    final Object fixedWidthSmallImage = imagesMap.get("fixed_width_small");
                    if (fixedWidthSmallImage != null && fixedWidthSmallImage instanceof Map) {
                        Map<String, String> fixedWidthSmallImageMap = (Map<String, String>) fixedWidthSmallImage;
                        imageData.setUrl(fixedWidthSmallImageMap.get("url"));
                    }
                }
                return imageData;
            }).collect(Collectors.toList());
        }
        return imageDataList;
    }
}
