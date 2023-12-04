package com.niksahn.laba5.controller;

import com.niksahn.laba5.service.FileService;
import com.niksahn.laba5.service.SessionService;
import com.niksahn.laba5.model.response.NewsResponse;
import com.niksahn.laba5.model.dto.NewsDto;
import com.niksahn.laba5.repository.ImagesRepository;
import com.niksahn.laba5.repository.NewsRepository;
import com.niksahn.laba5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.niksahn.laba5.Constants.news_path;


@RestController
@CrossOrigin
@RequestMapping("/news")
public class NewsController {
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final ImagesRepository imagesRepository;
    private final String defaultImg = news_path + "default";
    private final FileService fileService;

    private final NewsRepository newsRepository;

    @Autowired
    public NewsController(UserRepository userRepository, SessionService sessionService, ImagesRepository imagesRepository, FileService fileService, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.imagesRepository = imagesRepository;
        this.fileService = fileService;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/all")
    @CrossOrigin
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
        var images = imagesRepository.findByNewsId(newsDto.getId());
        ArrayList<String> imageList = new ArrayList<>();
        images.forEach(imagePath -> imageList.add(fileService.getImage(imagePath.getImage_path(), defaultImg)));
        if (imageList.size() == 0) {
            imageList.add(fileService.getImage("default",defaultImg));
        }
        return new NewsResponse(login, newsDto.getTitle(), newsDto.getDescription(), imageList);
    }

}

