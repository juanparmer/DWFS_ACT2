package com.example.ms_books_payments.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String category;
    private String isbn;
    private Integer rating;
    private Boolean visible;
    private Integer stock;
}
