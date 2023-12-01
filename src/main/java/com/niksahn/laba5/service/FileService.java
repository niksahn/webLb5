package com.niksahn.laba5.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.niksahn.laba5.Constants.image_path;

@Service
public class FileService {
    public FileService() {
    }

    public byte[] getImage(String name) {
        try {
            return Files.readAllBytes(Paths.get(image_path + "/" + name).normalize());
        } catch (IOException ignore) {
            ignore.printStackTrace();
            return null;
        }
    }

    public boolean setImage(MultipartFile file, String name) {
        try {
            file.transferTo(new File(image_path + "/" + name));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteImage(String name) {
        try {
            Files.deleteIfExists(Paths.get(image_path + "/" + name));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
