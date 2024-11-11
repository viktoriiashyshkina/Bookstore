package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name ="book_id")
  private Long id;

  private String author;

  private String bookName;

  private String ISBN;

  private String Category;

  private String description;

  private Double price;

  @OneToMany
  @JoinColumn(name = "book_id")
  private List<OrderDetailsEntity> orderDetails;

}
