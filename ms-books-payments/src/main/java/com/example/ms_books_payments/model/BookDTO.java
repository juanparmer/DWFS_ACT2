package com.example.ms_books_payments.model;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private Integer stock;
    private Boolean visible;
}
