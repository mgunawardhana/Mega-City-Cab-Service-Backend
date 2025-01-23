package com.megacity.backend.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Integer articleId;

    @NotBlank(message = "Article rating is required!")
    private double ratings;

    @NotBlank(message = "Article title is required!")
    private String title;

    @NotBlank(message = "Article description is required!")
    private String description;

    @NotBlank(message = "Article author is required!")
    private String author;

    @NotBlank(message = "Article media is required!")
    private String media;

    private Boolean is_active;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
