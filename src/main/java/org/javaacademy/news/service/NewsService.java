package org.javaacademy.news.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaacademy.news.dto.CreateNewsDtoRq;
import org.javaacademy.news.dto.NewsDtoRs;
import org.javaacademy.news.entity.Category;
import org.javaacademy.news.entity.News;
import org.javaacademy.news.mapper.NewsMapper;
import org.javaacademy.news.repository.CategoryRepository;
import org.javaacademy.news.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private final NewsMapper newsMapper;

    @Autowired
    private NewsService newsService;

    public boolean saveNews(CreateNewsDtoRq newsDtoRq) {
        try {
            newsService.createNews(newsDtoRq);
            return true;
        } catch (DataIntegrityViolationException e) {
            log.info(e.getMessage());
        }
        return false;
    }

    @Transactional
    public void createNews(CreateNewsDtoRq newsDtoRq) {
        News news = newsMapper.convertToEntity(newsDtoRq);
        Category category = categoryRepository.findByName(news.getCategory().getName()).orElse(null);
        if (category == null) {
            category = news.getCategory();
            categoryRepository.save(category);
        }
        category.getNewsSet().add(news);
        news.setCategory(category);
        newsRepository.save(news);
    }

    public List<NewsDtoRs> getNewsForToday() {
        LocalDate today = LocalDate.now();
        return newsMapper.convertToDto(newsRepository.findAllByDateOfNews(today));
    }

    public List<NewsDtoRs> getNewsByDateAndCategory(String date, String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElse(null);
        if (category == null) {
            return new ArrayList<>();
        }
        LocalDate newsDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return category.getNewsSet().stream()
                .filter(news -> news.getDateOfNews().equals(newsDate))
                .map(newsMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
