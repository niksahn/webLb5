package com.niksahn.laba5.controller;

import com.niksahn.laba5.manager.SessionService;
import com.niksahn.laba5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.niksahn.laba5.Constants.image_path;

@RestController
@RequestMapping("/file")
public class FileController {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    @Autowired
    public FileController(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    @PostMapping("/avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam MultipartFile file, String login, @RequestHeader("Authorization") Long session_id) {
        var user = userRepository.findByLogin(login);
        if (!sessionService.sessionIsValid(session_id, user.getId())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body("Неавторизованный пользователь");
        }
        String fileName = file.getOriginalFilename() + " " + login;
        try {
            file.transferTo(new File(image_path + fileName));
            if (user.getAvatar() != null) {
                Files.deleteIfExists(Paths.get(image_path + user.getAvatar()));
            }
            user.setAvatar(fileName);
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body("Ошибка загрузки файла");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body("Успешно");
    }

    @GetMapping("/avatar")
    @ResponseBody
    public ResponseEntity<byte[]> getAvatar(@RequestParam String login, @RequestHeader("Authorization") Long session_id) throws IOException {
        var user = userRepository.findByLogin(login);
        if (!sessionService.sessionIsValid(session_id, user.getId())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(null);
        }
        if (user.getAvatar() == null) {
            var imgFile = new ClassPathResource(image_path + "default.png");
            byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(bytes);
        } else {
            var imgFile = new ClassPathResource(image_path + user.getAvatar());
            byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }
    }
}

