package com.alikhan.e_commerce.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequestDTO(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be at least 0")
        Double price,

        @NotNull(message = "Category ID is required")
        Long categoryId
) {}
