package com.project.service;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import java.awt.print.Book;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<BookEntity> getAllBooks () {
    return bookRepository.findAll();
  }


  // edit code later, please do NOT DELETE!!!
/*  public Set<String> getAllBookCategories () {
    List<BookEntity> bookList = getAllBooks();
    TreeSet<String> sortedSetOfBookCategories = new TreeSet<>();
    for (BookEntity book : bookList) {
      sortedSetOfBookCategories.add(book.getCategory());
    }
    return sortedSetOfBookCategories;
  }*/

  public void saveBookToDatabase (BookEntity book) {
    bookRepository.save(book);
  }


  // Search for books by title or author
  @Transactional(readOnly = true)
  public List<BookEntity> searchBooks(String query) {
    return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
  }



}
