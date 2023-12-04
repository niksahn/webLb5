package com.niksahn.laba5.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static com.niksahn.laba5.Constants.imageType;
import static com.niksahn.laba5.Constants.image_path;

@Service
public class FileService {
    public FileService() {
    }

    public String getImage(String name, String defaultName) {
        try {
            return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(image_path + "/" + name + imageType).normalize()));
        } catch (IOException ignore) {
            // ignore.printStackTrace();
            try {
                return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(image_path + "/" + defaultName + imageType).normalize()));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public boolean setImage(String fileContent, String name) {
        try {
            byte[] file = Base64.getDecoder().decode(fileContent.getBytes(StandardCharsets.UTF_8));
            Files.write(Paths.get(image_path + "/" + name + imageType), file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
