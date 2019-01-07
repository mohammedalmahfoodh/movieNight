package com.movieevent.movienight.utilities;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.util.concurrent.TimeUnit;


/*  @Configuration
    @EnableWebMvc
    public class WebConfig   implements WebMvcConfigurer {

        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {
            registry.jsp("/WEB-INF/views/", ".jsp");
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {

            // Register resource handler for CSS and JS
            registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/statics/", "D:/statics/")
                    .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());

            // Register resource handler for images
            registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
                    .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {

           registry.addMapping("/user/**");

        }
    }*/

