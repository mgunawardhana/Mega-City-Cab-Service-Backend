package com.megacity.backend.domain.entity;

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
    private int guidanceId;

    private String title;

    private String description;

    private String category;

    private String priority;

    private String relatedTo;
}
