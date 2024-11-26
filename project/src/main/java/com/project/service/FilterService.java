package com.project.service;

import com.project.entity.BookEntity;
import com.project.entity.CategoryEntity;
import com.project.repository.BookRepository;
import com.project.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilterService {

  @Autowired
  private BookRepository bookRepository;

  @Transactional(readOnly = true)
  public Page<BookEntity> filterBooksByPriceRange(Double minPrice, Double maxPrice,
      org.springframework.data.domain.Pageable pageable) {
    return bookRepository.findByPriceBetween(minPrice, maxPrice, pageable);
  }

  // Get all books sorted alphabetically by title (A to Z)
  @Transactional(readOnly = true)
  public Page<BookEntity> getBooksSortedByTitle(Pageable pageable) {
    return bookRepository.findAllByOrderByTitleAsc(pageable);
  }

  @Transactional(readOnly = true)
  public Page<BookEntity> getBooksSortedByTitleDesc(Pageable pageable) {
    return bookRepository.findAllByOrderByTitleDesc(pageable);
  }

  // Get all books sorted by price (low to high)
  @Transactional(readOnly = true)
  public Page<BookEntity> getBooksSortedByPrice(Pageable pageable) {
    return bookRepository.findAllByOrderByPriceAsc(pageable);
  }

  @Transactional(readOnly = true)
  public Page<BookEntity> getBooksSortedByPriceDesc(Pageable pageable) {
    return bookRepository.findAllByOrderByPriceDesc(pageable);
  }

  // Get all books without filtering
  @Transactional(readOnly = true)
  public Page<BookEntity> getAllBooks(Pageable pageable) {
    return bookRepository.findAll(pageable); // Default, no filtering
  }

  @Transactional(readOnly = true)
  public List<BookEntity> filterByCategory(String category) {
    return bookRepository.findAllByCategoryContainsIgnoreCase(category, Pageable.unpaged());
  }
}


