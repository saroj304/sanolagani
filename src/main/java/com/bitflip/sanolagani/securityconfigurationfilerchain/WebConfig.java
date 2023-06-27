package com.bitflip.sanolagani.securityconfigurationfilerchain;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/documents/**",
                        "/images/**",
                        "/css/**",
                        "/js/**")
                .addResourceLocations(
                        "classpath:/documents/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/images/");
    }

 
}