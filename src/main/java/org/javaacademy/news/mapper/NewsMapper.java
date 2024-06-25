package org.javaacademy.news.mapper;

import org.javaacademy.news.dto.CreateNewsDtoRq;
import org.javaacademy.news.dto.NewsDtoRs;
import org.javaacademy.news.entity.Category;
import org.javaacademy.news.entity.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    @Mapping(target = "category", source = "categoryName", qualifiedByName = "createCategory")
    @Mapping(target = "dateOfNews", source = "date", qualifiedByName = "getDateOfNews")
    News convertToEntity(CreateNewsDtoRq newsDtoRq);

    @Named("createCategory")
    default Category createCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        return category;
    }

    @Named("getDateOfNews")
    default LocalDate createDateOfNews(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Mapping(target = "categoryName", source = ".", qualifiedByName = "getCategoryName")
    NewsDtoRs convertToDto(News news);

    List<NewsDtoRs> convertToDto(List<News> news);

    @Named("getCategoryName")
    default String getCategoryName(News news) {
        return news.getCategory().getName();
    }
}
