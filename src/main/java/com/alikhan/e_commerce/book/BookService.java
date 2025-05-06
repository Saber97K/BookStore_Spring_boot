package com.alikhan.e_commerce.book;

import com.alikhan.e_commerce.Category.Category;
import com.alikhan.e_commerce.Category.CategoryRepository;
import com.alikhan.e_commerce.book.dto.BookRequestDTO;
import com.alikhan.e_commerce.book.dto.BookResponseDTO;
import com.alikhan.e_commerce.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;


    // ✅ Create
    public BookResponseDTO createBook(BookRequestDTO request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Book book = BookMapper.toEntity(request,category);

        return BookMapper.toDto(bookRepository.save(book));
    }

    // ✅ Read all
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    // ✅ Read by ID
    public BookResponseDTO getBookById(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return BookMapper.toDto(book);
    }

    // ✅ Update
    public BookResponseDTO updateBook(Long id, BookRequestDTO request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));


        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        book.updatePrice(request.price());
        book.changeCategory(category);
        /*
         Optional: title and author updates
         book.updateTitle(request.title());
         book.updateAuthor(request.author());
        */

        return BookMapper.toDto(bookRepository.save(book));
    }
    // ✅ Delete
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    // ✅ findByTitle (Spring method naming)
    public List<BookResponseDTO> searchByTitle(String title) {
        return bookRepository.findByTitle(title)
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    // ✅ findBooksInPriceRange (custom JPQL)
    public List<BookResponseDTO> getBooksInPriceRange(Double minPrice, Double maxPrice) {
        return bookRepository.findBooksInPriceRange(minPrice, maxPrice)
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    // ✅ findByCategoryId + Pageable
    public Page<BookResponseDTO> getBooksByCategoryPaged(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findByCategoryId(categoryId, pageable)
                .map(BookMapper::toDto);
    }

    // ✅ Sorting by price ascending
    public List<BookResponseDTO> getBooksSortedByPrice() {
        return bookRepository.findAllByOrderByPriceAsc()
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    // ✅ Native query (search by author)
    public List<BookResponseDTO> searchBooksByAuthorNative(String author) {
        return bookRepository.searchByAuthorNative(author)
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    // ✅ Combined: JPQL + pagination + sorting
    public Page<BookResponseDTO> searchBooksByTitlePaged(String keyword, int page, int size, String sortBy, boolean asc) {
        Sort sort = asc ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.searchByTitle(keyword, pageable)
                .map(BookMapper::toDto);
    }

}
