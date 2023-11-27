package com.niksahn.laba5.controller;

import com.niksahn.laba5.manager.FileService;
import com.niksahn.laba5.manager.SessionService;
import com.niksahn.laba5.model.NewsResponse;
import com.niksahn.laba5.model.dto.NewsDto;
import com.niksahn.laba5.repository.NewsRepository;
import com.niksahn.laba5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@CrossOrigin
@RequestMapping("/news")
public class NewsController {
    private final UserRepository userRepository;
    private final SessionService sessionService;

    private final FileService fileService;

    private final NewsRepository newsRepository;

    @Autowired
    public NewsController(UserRepository userRepository, SessionService sessionService, FileService fileService, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.fileService = fileService;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllNews() {
        var newsList = newsRepository.findAll();
        ArrayList<NewsResponse> response = new ArrayList<NewsResponse>();
        newsList.forEach(news -> response.add(fromNewsDto(news)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    NewsResponse fromNewsDto(NewsDto newsDto) {
        String login = null;
        if (userRepository.findById(newsDto.getUserId()).isPresent()) {
            login = userRepository.findById(newsDto.getUserId()).get().getLogin();
        }
        var image = fileService.getImage(newsDto.getImage());
        return new NewsResponse(login, newsDto.getTitle(), newsDto.getDescription(), image);
    }

}

