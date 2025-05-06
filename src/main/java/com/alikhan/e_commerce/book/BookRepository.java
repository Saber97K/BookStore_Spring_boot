package com.alikhan.e_commerce.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // ✅ 1. Method name-based query (Spring builds this automatically)
    Optional<Book> findByTitle(String title);

    List<Book> findByAuthorAndPriceLessThan(String title, BigDecimal price);

    // ✅ 2. Custom query with @Query (JPQL version)
    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    List<Book> findBooksInPriceRange(@Param("minPrice") Double minPrice,@Param("maxPrice") Double maxPrice);

    // ✅ 3. Pagination (limit 5 per page, handled in service/controller)
    Page<Book> findByCategoryId(Long categoryId, Pageable pageable);

    // ✅ 4. Sorting (also passed via Pageable or Sort parameter)
    List<Book> findAllByOrderByPriceAsc(); // or use `Sort` param for dynamic sorting

    @Query(value = "SELECT * FROM book WHERE author LIKE %:author%", nativeQuery = true)
    List<Book> searchByAuthorNative(String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Book> searchByTitle(@Param("keyword") String keyword, Pageable pageable);
}
