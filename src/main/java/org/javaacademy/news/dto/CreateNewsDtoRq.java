package org.javaacademy.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewsDtoRq {
    private String categoryName;
    private String title;
    private String description;
    private String date;
}
