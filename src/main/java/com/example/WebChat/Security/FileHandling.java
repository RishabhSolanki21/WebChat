package com.example.WebChat.Security;


import jakarta.annotation.Resource;
import jdk.jfr.ContentType;
import org.apache.catalina.webresources.FileResource;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileHandling {

    private final static Path upload_dir= Paths.get("/uploads");
    private final static Logger log= LoggerFactory.getLogger(FileHandling.class);

    public String  filesave(MultipartFile file) throws IOException {
        String filename= UUID.randomUUID().toString()+file.getOriginalFilename();
        Path path=upload_dir.resolve(filename).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
        }
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        log.info("{} saved {}",filename, path.toUri());
        return filename;
    }

    public ResponseEntity<UrlResource> retriveFile(String filename) throws IOException {
        Path file=upload_dir.resolve(filename).toAbsolutePath().normalize();
        String contentType=Files.probeContentType(file);
        UrlResource resource=new UrlResource(file.toUri());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }
}
