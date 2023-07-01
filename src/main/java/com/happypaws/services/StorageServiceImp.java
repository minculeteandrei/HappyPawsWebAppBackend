package com.happypaws.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class StorageServiceImp implements StorageService{

    private final ServletContext servletContext;

    public StorageServiceImp(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void save(MultipartFile file) throws IOException {
        try{

            Path rootLocation = Paths.get("")
                    .toAbsolutePath()
                    .getParent()
                    .resolve(Paths.get("frontend/src/assets/images/products"));
            Path destination = rootLocation.resolve(Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
                    .normalize().toAbsolutePath();
            if(!destination.getParent().equals(rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside of current directory");
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream,destination, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e){
            e.printStackTrace();
            throw new IOException("Could not save image file:"+  file.getOriginalFilename(), e);
        }
    }

    @Override
    public byte[] load(String filename) {
        var imgFile = new ClassPathResource(filename);
        try {
            return StreamUtils.copyToByteArray(imgFile.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
