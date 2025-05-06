package com.alikhan.e_commerce.book.dto;

import lombok.Builder;

@Builder
public record BookResponseDTO(
        Long id,
        String title,
        String author,
        Double price,
        String categoryName
) {
}
