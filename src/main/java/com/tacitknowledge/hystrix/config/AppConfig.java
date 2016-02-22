package com.tacitknowledge.hystrix.config;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @author Daniel Valencia (daniel@tacitknowledge.com)
 */
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new HystrixMetricsStreamServlet());
        servletRegistrationBean.setName("HystrixMetricsStreamServlet");
        servletRegistrationBean.setUrlMappings(Collections.singletonList("/hystrix.stream"));
        return servletRegistrationBean;
    }
}
