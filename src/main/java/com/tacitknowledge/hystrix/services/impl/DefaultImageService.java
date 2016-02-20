package com.tacitknowledge.hystrix.services.impl;

import com.google.gson.Gson;
import com.tacitknowledge.hystrix.models.ImageData;
import com.tacitknowledge.hystrix.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Service
public class DefaultImageService implements ImageService {

    @Value("http://api.giphy.com/")
    private String baseUrl;

    @Value("/v1/gifs/random")
    private String randomPath;

    @Value("dc6zaTOxFJmzC")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Override
    public List<ImageData> fetchAllImages() {
        throw new NotImplementedException();
    }

    @Override
    public ImageData fetchRandomImage(String tag) {
        String randomImagePath = buildRandomImagePath(tag);
        String response = executeRequest(randomImagePath);
        System.out.println("Hello world");
        return unmarshalResponse(response);
    }

    private ImageData unmarshalResponse(String response) {
        final ImageData imageData = new ImageData();
        Map<String, Object> imageMap = gson.fromJson(response, Map.class);

        if(imageMap != null) {
            final Object data = imageMap.get("data");
            if (data != null && data instanceof Map) {
                Map<String, String> dataMap = (Map<String, String>) data;
                imageData.url = dataMap.get("image_url");
                imageData.alt = dataMap.get("caption");
            }
        }

        return imageData;
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
}
