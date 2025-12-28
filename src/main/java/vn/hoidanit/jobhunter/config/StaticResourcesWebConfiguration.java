package vn.hoidanit.jobhunter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {
    @Value("${base-uri}")
    private String baseURI;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve files from external storage path
        registry.addResourceHandler("/storage/**")
                .addResourceLocations(baseURI);
        
        // Serve images from uploads/images directory
        // This allows access to images via /uploads/images/** URL
        // Use absolute path to ensure it works in both development and production
        String workingDir = System.getProperty("user.dir");
        String imagesPath = workingDir + "/uploads/images/";
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:" + imagesPath)
                .setCachePeriod(3600);
    }
}
