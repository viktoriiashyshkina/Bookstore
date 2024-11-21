package com.project.repository;

import com.project.entity.BookEntity;
import java.awt.print.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
//  @Query("SELECT new com.project.entity.BookEntity(b.id, b.author, b.title, b.isbn, b.category, b.description, b.price, b.orderDetails, b.image, b.imageDataBase64) " +
//      "FROM BookEntity b " +
//      "WHERE UPPER(b.title) LIKE UPPER(CONCAT('%', :query, '%')) " +
//      "OR UPPER(b.author) LIKE UPPER(CONCAT('%', :query, '%'))")
  // Search books by title or author
  List<BookEntity> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String query, String author);

}
