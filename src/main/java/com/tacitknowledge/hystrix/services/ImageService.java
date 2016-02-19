package com.tacitknowledge.hystrix.services;

import com.tacitknowledge.hystrix.models.ImageData;

import java.util.List;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
public interface ImageService {
    List<ImageData> fetchAllImages();
    ImageData fetchRandomImage(String tag);
}
