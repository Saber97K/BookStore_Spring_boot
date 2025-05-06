package com.alikhan.e_commerce.book;

import com.alikhan.e_commerce.book.dto.BookRequestDTO;
import com.alikhan.e_commerce.book.dto.BookResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookRequestDTO request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }


    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookRequestDTO request
    ) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Search by title
    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }

    // ✅ Filter by price range
    @GetMapping("/filter/price-range")
    public ResponseEntity<List<BookResponseDTO>> getBooksInPriceRange(
            @RequestParam Double min,
            @RequestParam Double max
    ) {
        return ResponseEntity.ok(bookService.getBooksInPriceRange(min, max));
    }

    // ✅ Get books by category with pagination
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<BookResponseDTO>> getBooksByCategoryPaged(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(bookService.getBooksByCategoryPaged(categoryId, page, size));
    }

    // ✅ Get books sorted by price
    @GetMapping("/sorted-by-price")
    public ResponseEntity<List<BookResponseDTO>> getBooksSortedByPrice() {
        return ResponseEntity.ok(bookService.getBooksSortedByPrice());
    }

    // ✅ Native query: search by author
    @GetMapping("/native-author")
    public ResponseEntity<List<BookResponseDTO>> searchBooksByAuthorNative(@RequestParam String author) {
        return ResponseEntity.ok(bookService.searchBooksByAuthorNative(author));
    }

    // ✅ Combined: search by title with pagination and sorting
    @GetMapping("/search/title-paged")
    public ResponseEntity<Page<BookResponseDTO>> searchBooksByTitlePaged(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc
    ) {
        return ResponseEntity.ok(bookService.searchBooksByTitlePaged(keyword, page, size, sortBy, asc));
    }
}
