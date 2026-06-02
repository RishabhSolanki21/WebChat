package com.example.WebChat.Security;


import com.example.WebChat.Dto.FileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileHandling {

    private final static Path upload_dir= Paths.get("/uploads");
    private final static Logger log= LoggerFactory.getLogger(FileHandling.class);

    public FileDto filesave(MultipartFile file) throws IOException {
        String filename= UUID.randomUUID()+file.getOriginalFilename();
        Path path=upload_dir.resolve(filename).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
        }
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        log.info("{} saved {}",filename, path.toUri());
        String contentType=file.getContentType();
        return new FileDto(filename,contentType);
    }

    public ResponseEntity<UrlResource> retriveFile(String filename) throws IOException {
        Path file=upload_dir.resolve(filename).toAbsolutePath().normalize();
        String contentType=Files.probeContentType(file);
        log.info("contentType={}", contentType);
        UrlResource resource=new UrlResource(file.toUri());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header("Cache-Control","private,max-age=86400")
                .body(resource);
    }
}
