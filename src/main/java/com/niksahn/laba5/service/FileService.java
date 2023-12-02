package com.niksahn.laba5.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static com.niksahn.laba5.Constants.image_path;

@Service
public class FileService {
    public FileService() {
    }

    public String getImage(String name) {
        try {
            return new String(Base64.getEncoder().encode(Files.readAllBytes(Paths.get(image_path + "/" + name).normalize())));

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

    public boolean setImage(byte[] file, String name) {
        var file1 = new File(Paths.get(image_path + "/" + name).toString());
        try {
            if (!file1.exists()) file1.createNewFile();
            OutputStream outputStream = Files.newOutputStream(Paths.get(image_path + "/" + name));
            outputStream.write(file, 0, file.length);
            return true;
        } catch (IOException e) {
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
