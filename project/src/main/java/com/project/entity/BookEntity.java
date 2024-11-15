package com.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name ="book_id")
  private Long id;

  private String author;

  private String title;

  private String isbn;

  private String category;

  private String description;

  private BigDecimal price;

  @OneToMany
  @JoinColumn(name = "book_id")
  private List<OrderDetailsEntity> orderDetails;

  public Long getId() {
    return id;
  }
//private String image; // Store image path or URL if needed

  @Lob // Annotate as a large object
  @Column(name = "image", columnDefinition = "oid")
  private byte[] image; // Store the image as a byte array


  private String imageDataBase64;

}
