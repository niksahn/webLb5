package com.niksahn.laba5.service;

import com.niksahn.laba5.model.OperationRezult;
import com.niksahn.laba5.model.Role;
import com.niksahn.laba5.model.dto.ImageNewsDto;
import com.niksahn.laba5.model.dto.NewsDto;
import com.niksahn.laba5.repository.ImagesRepository;
import com.niksahn.laba5.repository.NewsRepository;
import com.niksahn.laba5.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

import static com.niksahn.laba5.Constants.news_path;

@Service
public class NewsService {
    private final ImagesRepository imagesRepository;

    private final UserRepository userRepository;

    private final FileService fileService;
    private final NewsRepository newsRepository;

    public NewsService(ImagesRepository imagesRepository, UserRepository userRepository, FileService fileService, NewsRepository newsRepository) {
        this.imagesRepository = imagesRepository;
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.newsRepository = newsRepository;
    }

    public OperationRezult addNew(String name, String description, ArrayList<String> images, Long user_id) {
        var user = userRepository.findByUserId(user_id);
        NewsDto news;
        if (user.getRole() == Role.admin || user.getRole() == Role.moderator) {
            news = newsRepository.save(new NewsDto(user_id, name, description));
        } else return OperationRezult.No_Right;
        images.forEach(it -> {
                    String nameFile = news_path + news.getId() + "_" + Random.from(RandomGenerator.getDefault()).nextLong();
                    var file = fileService.setImage(it, nameFile);
                    imagesRepository.save(new ImageNewsDto(file, news.getId()));
                }
        );
        return OperationRezult.Success;
    }

    public OperationRezult editNew(Long id, String name, String description, ArrayList<String> images, Long user_id) {
        var user = userRepository.findByUserId(user_id);
        var newD = newsRepository.findById(id);
        if (newD.isEmpty()) return OperationRezult.Not_In_DataBase;
        NewsDto news;
        if (user.getRole() == Role.admin || user.getRole() == Role.moderator) {
            news = newsRepository.save(new NewsDto(id, user_id, name, description));
        } else return OperationRezult.No_Right;
        imagesRepository.findByNewsId(id).forEach(it -> {
            fileService.deleteImage(it.getImage_path());
            imagesRepository.delete(it);
        });
        images.forEach(it -> {
                    String nameFile = name + "_" + Random.from(RandomGenerator.getDefault()).nextLong();
                    var file = fileService.setImage(it, nameFile);
                    imagesRepository.save(new ImageNewsDto(file, news.getId()));
                }
        );
        return OperationRezult.Success;
    }

    @Transactional
    public OperationRezult dellNew(Long new_id, Long user_id) {
        var user = userRepository.findByUserId(user_id);
        var news = newsRepository.findById(new_id);
        if (user.getRole() == Role.admin || user.getRole() == Role.moderator) {
        } else return OperationRezult.No_Right;
        if (news.isEmpty()) return OperationRezult.Not_In_DataBase;
        var images = imagesRepository.findByNewsId(new_id);
        images.forEach(it -> {
                    fileService.deleteImage(it.getImage_path());
                    imagesRepository.delete(it);
                }
        );
        newsRepository.deleteById(new_id);
        return OperationRezult.Success;
    }

}
