package com.niksahn.laba5.controller;

import com.niksahn.laba5.model.OperationRezult;
import com.niksahn.laba5.model.request.AddNewsRequest;
import com.niksahn.laba5.service.FileService;
import com.niksahn.laba5.service.NewsService;
import com.niksahn.laba5.service.SessionService;
import com.niksahn.laba5.model.response.NewsResponse;
import com.niksahn.laba5.model.dto.NewsDto;
import com.niksahn.laba5.repository.ImagesRepository;
import com.niksahn.laba5.repository.NewsRepository;
import com.niksahn.laba5.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.niksahn.laba5.Constants.news_path;
import static com.niksahn.laba5.controller.Common.checkAuth;
import static com.niksahn.laba5.controller.Common.validate;


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

    private final NewsService newsService;

    @Autowired
    public NewsController(UserRepository userRepository, SessionService sessionService, ImagesRepository imagesRepository, FileService fileService, NewsRepository newsRepository, NewsService newsService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.imagesRepository = imagesRepository;
        this.fileService = fileService;
        this.newsRepository = newsRepository;
        this.newsService = newsService;
    }

    @GetMapping("/all")
    @CrossOrigin
    public ResponseEntity<?> getAllNews() {
        var newsList = newsRepository.findAll();
        ArrayList<NewsResponse> response = new ArrayList<NewsResponse>();
        newsList.forEach(news -> response.add(fromNewsDto(news)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<?> addNew(@RequestHeader("Authorization") Long session_id, @RequestBody @Valid AddNewsRequest request, BindingResult bindingResult) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        var valid = validate(bindingResult);
        if (valid != null) return valid;
        var operation = newsService.addNew(request.title, request.description, request.images, request.user_id);
        if (operation == OperationRezult.Success) return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(operation.toString());
    }

    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<?> editNew(@RequestHeader("Authorization") Long session_id, @RequestBody @Valid AddNewsRequest request, @RequestParam Long id, BindingResult bindingResult) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        var valid = validate(bindingResult);
        if (valid != null) return valid;
        var operation = newsService.editNew(id, request.title, request.description, request.images, request.user_id);
        if (operation == OperationRezult.Success) return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(operation.toString());
    }

    @PostMapping("/delete")
    @CrossOrigin
    public ResponseEntity<?> dellNew(@RequestHeader("Authorization") Long session_id, @RequestParam Long id) {
        var auth = checkAuth(session_id, sessionService);
        if (auth != null) return auth;
        var user_id = sessionService.getUserIdFromSession(session_id);
        var operation = newsService.dellNew(id, user_id);
        if (operation == OperationRezult.Success) return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(operation.toString());
    }

    NewsResponse fromNewsDto(NewsDto newsDto) {
        String login = null;
        if (userRepository.findById(newsDto.getUserId()).isPresent()) {
            login = userRepository.findById(newsDto.getUserId()).get().getLogin();
        }
        var images = imagesRepository.findByNewsId(newsDto.getId());
        ArrayList<ArrayList<String>> imageList = new ArrayList<>();
        images.forEach(imagePath -> imageList.add(fileService.getImage(imagePath.getImage_path(), defaultImg)));
        if (imageList.isEmpty()) {
            imageList.add(fileService.getImage("default.png", defaultImg));
        }
        return new NewsResponse(newsDto.getId(), login, newsDto.getTitle(), newsDto.getDescription(), imageList);
    }

}

