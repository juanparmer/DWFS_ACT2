package com.relatosdepapel.books.service;

import com.relatosdepapel.books.controller.model.BookDto;
import com.relatosdepapel.books.controller.model.BookRequest;
import com.relatosdepapel.books.data.BookRepository;
import com.relatosdepapel.books.data.model.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Autowired
    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }


    // java
    @Override
    public Book create(BookRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request cannot be null");
        }

        String title = request.getTitle() != null ? request.getTitle().trim() : "";
        String author = request.getAuthor() != null ? request.getAuthor().trim() : "";
        String category = request.getCategory() != null ? request.getCategory().trim() : "";


        if (!StringUtils.hasLength(title)
                || !StringUtils.hasLength(author)
                || !StringUtils.hasLength(category)
                || request.getVisible() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required book fields");
        }

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setCategory(category);
        book.setVisible(request.getVisible());


        if (request.getPublicationDate() != null) {
            book.setPublicationDate(request.getPublicationDate());
        }
        if (request.getIsbn() != null) {
            book.setIsbn(request.getIsbn().trim());
        }
        if (request.getRating() != null) {
            book.setRating(request.getRating());
        }
        if (request.getStock() != null) {
            book.setStock(request.getStock());
        }


        return repository.save(book);
    }


    @Override
    public Book update(Long id, @Valid BookDto dto) {
        Book existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        existing.updateFromDto(dto);
        return repository.save(existing);
    }

    @Override
    public Book patch(Long id, BookDto dto) {
        Book existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
        existing.patchFromDto(dto);
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Book> search(Map<String, String> filters) {
        return repository.search(filters);
    }

    @Override
    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }
}