package com.project.service;

import com.project.entity.BookEntity;
import com.project.repository.BookRepository;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final BookRepository bookRepository;

  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<BookEntity> getAllBooks () {
    return bookRepository.findAll();
  }

  public Set<String> getAllBookCategories () {
    List<BookEntity> bookList = getAllBooks();
    TreeSet<String> sortedSetOfBookCategories = new TreeSet<>();
    for (BookEntity book : bookList) {
      sortedSetOfBookCategories.add(book.getCategory());
    }
    return sortedSetOfBookCategories;
  }

  public void saveBookToDatabase (BookEntity book) {
    bookRepository.save(book);
  }



}
