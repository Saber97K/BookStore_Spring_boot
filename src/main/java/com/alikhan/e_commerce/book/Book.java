package com.alikhan.e_commerce.book;

import com.alikhan.e_commerce.Category.Category;
import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(name = "book_seq", sequenceName = "book_sequence", allocationSize = 1)

public class Book {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    private Long id;

    private String title;
    private String author;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /*
    @Builder
    public Book(String title, String author, Double price, Category category) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
    }
    */

    public static Book of(String title, String author, Double price, Category category) {
        Book book = new Book();
        book.title = title;
        book.author = author;
        book.price = price;
        book.category = category;
        return book;
    }

    public void updatePrice(Double newPrice) {
        this.price = newPrice;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

}
