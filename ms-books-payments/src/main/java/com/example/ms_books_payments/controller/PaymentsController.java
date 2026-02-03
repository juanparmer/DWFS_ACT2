package com.example.ms_books_payments.controller;

import com.example.ms_books_payments.model.BookDTO;
import com.example.ms_books_payments.model.Purchase;
import com.example.ms_books_payments.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PaymentsController {

    private final PurchaseRepository repository;
    private final RestTemplate restTemplate;

    @PostMapping("/payments")
    public ResponseEntity<Purchase> registerPurchase(@RequestBody Long bookId) {
        // Usamos el NOMBRE del microservicio registrado en Eureka
        // String url = "http://ms-books-catalogue/books/" + bookId;
        String url = "http://localhost:8088/books/" + bookId;
        
        try {
            BookDTO book = restTemplate.getForObject(url, BookDTO.class);

            // Validación: Que el libro exista, tenga stock y no esté oculto
            if (book != null && book.getStock() > 0 && book.getVisible()) {
                Purchase purchase = new Purchase();
                purchase.setBookId(bookId);
                purchase.setPurchaseDate(LocalDateTime.now());
                
                // Persistencia en base de datos relacional
                return ResponseEntity.ok(repository.save(purchase));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.badRequest().build();
    }
}
