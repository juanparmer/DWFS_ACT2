package com.example.ms_books_payments.repository;

import com.example.ms_books_payments.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
