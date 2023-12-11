package com.niksahn.laba5.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import static com.niksahn.laba5.Constants.*;

@Service
public class FileService {
    public FileService() {
    }

    public ArrayList<String> getImage(String name, String defaultName) {
        var a = new ArrayList<String>();
        try {
            a.add(name.split("\\.")[1]);
            a.add(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(image_path + "/" + name).normalize())));
            return a;
        } catch (Exception ignore) {
            // ignore.printStackTrace();
            try {
                a.add(defaultImageType);
                a.add(Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(image_path + "/" + defaultName + defaultImageType).normalize())));
                return a;
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

    public String setImage(String fileContent, String name) {
        try {
            var image = fileContent.split(",");
            String imageType = "." + image[0].split("/|;")[1];
            byte[] file = Base64.getDecoder().decode(image[1].getBytes(StandardCharsets.UTF_8));
            Files.write(Paths.get(image_path + "/" + name + imageType), file);
            return name + imageType;
        } catch (Exception e) {
            e.printStackTrace();
            return avatar_path + "default" + defaultImageType;
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
