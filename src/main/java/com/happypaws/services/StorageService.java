package com.happypaws.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void save(MultipartFile file, String filename) throws IOException;
    byte[] load(String filename);
}
