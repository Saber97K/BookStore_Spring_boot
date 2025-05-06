package com.alikhan.e_commerce.Category.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {
    private Long id;

    @NotBlank
    private String name;

    // Getters and Setters
}
