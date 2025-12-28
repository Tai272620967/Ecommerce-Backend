package vn.hoidanit.jobhunter.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    @Value("${base-uri}")
    private String baseURI;
    
    public void createDirectory(String folder) throws URISyntaxException, IOException {
        System.out.println("createDirectory: " + folder);
        
        // folder is already a normal path (no file:// prefix), use directly
        Path path = Paths.get(folder);
        
        // Create all parent directories if they don't exist
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Create new directory successfully, path = " + path.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + path.toAbsolutePath());
                e.printStackTrace();
                throw e;
            }
        } else {
            System.out.println("Directory already exists: " + path.toAbsolutePath());
        }
    }

    public String store(MultipartFile file, String folder) throws URISyntaxException, IOException {
        // Create directory first if it doesn't exist
        // Use relative path if baseURI is file:// URI, otherwise use as is
        String folderPath;
        if (baseURI.startsWith("file://")) {
            // For Docker container, use working directory + folder
            String workingDir = System.getProperty("user.dir");
            folderPath = workingDir + "/" + folder;
        } else {
            folderPath = baseURI + folder;
        }
        System.out.println("store - folderPath: " + folderPath);
        createDirectory(folderPath);
        
        // create unique filename
        String finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        // Build file path - folderPath is already processed (no file:// prefix)
        String filePath = folderPath + "/" + finalName;
        System.out.println("store - filePath: " + filePath);
        Path path = Paths.get(filePath);
        System.out.println("store - absolute path: " + path.toAbsolutePath());
        
        // Create parent directories for the file if they don't exist
        Path parentDir = path.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            System.out.println("Creating parent directory: " + parentDir.toAbsolutePath());
            Files.createDirectories(parentDir);
        }
        
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved successfully to: " + path.toAbsolutePath());
        }

        return finalName;
    }
}
