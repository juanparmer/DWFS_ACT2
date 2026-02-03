package com.relatosdepapel.books.service;

import com.relatosdepapel.books.controller.model.BookDto;
import com.relatosdepapel.books.controller.model.BookRequest;
import com.relatosdepapel.books.data.model.Book;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface BookService {

    Book create(BookRequest book);

    Book update(Long id, @Valid BookDto dto);

    Book patch(Long id, BookDto dto);

    void delete(Long id);

    List<Book> search(Map<String, String> filters);

    Book findById(Long id);
}
