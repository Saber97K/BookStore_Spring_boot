package com.alikhan.e_commerce.book;

import com.alikhan.e_commerce.Category.Category;
import com.alikhan.e_commerce.book.dto.BookResponseDTO;
import com.alikhan.e_commerce.book.dto.BookRequestDTO;

public class BookMapper {

    public static BookResponseDTO toDto(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getCategory().getName()
        );
    }

    /*
    // From DTO to Entity
    public static Book toEntity(BookRequestDTO dto, Category category) {
        return Book.builder()
                .title(dto.title())
                .author(dto.author())
                .price(dto.price())
                .category(category)
                .build();
    }

     */

    // ✅ Use factory method instead of builder
    public static Book toEntity(BookRequestDTO dto, Category category) {
        return Book.of(
                dto.title(),
                dto.author(),
                dto.price(),
                category
        );
    }
}
