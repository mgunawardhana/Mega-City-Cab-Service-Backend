package com.megacity.backend.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guideline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int guidanceId;

    private String title;

    private String description;

    private String category;

    private String priority;

    private String relatedTo;
}
