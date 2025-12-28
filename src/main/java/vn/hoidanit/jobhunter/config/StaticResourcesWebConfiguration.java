package vn.hoidanit.jobhunter.config;

import java.nio.file.Paths;

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
        
        // Serve images from uploads/images directory (including products subdirectory)
        // This allows access to images via /uploads/images/** URL
        // Use working directory in container instead of baseURI (which points to host path)
        String workingDir = System.getProperty("user.dir");
        String imagesPath = workingDir + "/uploads/images/";
        
        // Convert to absolute path and ensure it ends with /
        java.nio.file.Path absolutePath = Paths.get(imagesPath).toAbsolutePath();
        String normalizedPath = absolutePath.toString().replace("\\", "/");
        if (!normalizedPath.endsWith("/")) {
            normalizedPath += "/";
        }
        
        // Use file:// URI format for Spring ResourceHandler
        String fileUri = "file://" + normalizedPath;
        
        System.out.println("StaticResourcesWebConfiguration - Serving images from: " + fileUri);
        System.out.println("StaticResourcesWebConfiguration - Working directory: " + workingDir);
        System.out.println("StaticResourcesWebConfiguration - Absolute path: " + absolutePath);
        
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations(fileUri)
                .setCachePeriod(3600);
    }
}
