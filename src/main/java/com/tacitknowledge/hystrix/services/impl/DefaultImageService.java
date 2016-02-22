package com.tacitknowledge.hystrix.services.impl;

import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.parsers.ImageDataParser;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Service
public class DefaultImageService implements ImageService {

    @Value("http://api.giphy.com/")
    private String baseUrl;

    @Value("/v1/gifs/random")
    private String randomPath;

    @Value("/v1/gifs/search")
    private String searchPath;

    @Value("dc6zaTOxFJmzC")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ImageDataParser imageDataParser;

    @Override
    public List<ImageData> searchImages(String searchTerm) {
        String searchPath = buildSearchPath(searchTerm);
        String response = executeRequest(searchPath);
        return imageDataParser.fromStringToList(response);
    }

    @Override
    public ImageData fetchRandomImage(String tag) {
        String randomImagePath = buildRandomImagePath(tag);
        String response = executeRequest(randomImagePath);
        return imageDataParser.fromString(response);
    }

    private String executeRequest(String path) {
        String response = null;

        try {
            response = restTemplate.getForObject(path, String.class, (Object) null);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return response;
    }

    private String buildRandomImagePath(String tag) {
        return String.format("%s%s?api_key=%s&tag=%s", baseUrl, randomPath, apiKey, tag);
    }

    private String buildSearchPath(String searchTerm) {
        return String.format("%s%s?api_key=%s&q=%s", baseUrl, searchPath, apiKey, searchTerm);
    }
}
