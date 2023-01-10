package com.happypaws.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageServiceImp implements StorageService{

    private final ServletContext servletContext;

    public StorageServiceImp(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public void save(MultipartFile file, String filename) throws IOException {
        String folder = "src/main/resources/static/photos/products/";
        //Path path = Paths.get(folder);
        try(FileOutputStream output = new FileOutputStream(folder + filename);){
            output.write(file.getBytes());
        }catch (IOException e){
            throw new IOException("Could not save image file:"+  file.getOriginalFilename(), e);
        }
    }

    @Override
    public byte[] load(String filename) {
        var imgFile = new ClassPathResource(filename);
        System.out.println(imgFile);
        try {
            return StreamUtils.copyToByteArray(imgFile.getInputStream());
        } catch (IOException e) {
            System.out.println("error here");
            throw new RuntimeException(e);
        }
    }
}
