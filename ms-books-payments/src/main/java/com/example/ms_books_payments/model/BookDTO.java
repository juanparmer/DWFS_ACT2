package com.example.ms_books_payments.model;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private Integer stock;    // Importante para la validación
    private Boolean visible;  // Importante para la validación
}
