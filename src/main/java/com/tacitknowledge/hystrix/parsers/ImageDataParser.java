package com.tacitknowledge.hystrix.parsers;

import com.tacitknowledge.hystrix.models.ImageData;

import java.util.List;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
public interface ImageDataParser {
    ImageData fromString(String imageContent);
    List<ImageData> fromStringToList(String imageContent);
}
