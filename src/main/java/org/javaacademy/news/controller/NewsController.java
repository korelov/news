package org.javaacademy.news.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.news.dto.CreateNewsDtoRq;
import org.javaacademy.news.dto.NewsDtoRs;
import org.javaacademy.news.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping("/news")
    public ResponseEntity<?> createNews(@RequestBody CreateNewsDtoRq newsDtoRq) {
        if (newsService.saveNews(newsDtoRq)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/today")
    public ResponseEntity<List<NewsDtoRs>> getNewsForToday() {
        return ResponseEntity.ok().body(newsService.getNewsForToday());
    }

    @GetMapping("/news")
    public ResponseEntity<List<NewsDtoRs>> getNewsByDateAndCategory(@RequestParam String date,
                                                                    @RequestParam String categoryName) {
        List<NewsDtoRs> newsByDateAndCategory = newsService.getNewsByDateAndCategory(date, categoryName);
        return ResponseEntity.ok().body(newsByDateAndCategory);
    }
}
