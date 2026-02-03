// java
package com.relatosdepapel.books.data;

import com.relatosdepapel.books.data.model.Book;
import com.relatosdepapel.books.data.BookJpaRepository;
import com.relatosdepapel.books.data.utils.SearchCriteria;
import com.relatosdepapel.books.data.utils.SearchOperation;
import com.relatosdepapel.books.data.utils.SearchStatement;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepository {

    private final BookJpaRepository jpaRepository;

    public BookRepository(BookJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public Book save(Book book) {
        return jpaRepository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return jpaRepository.findById(id);
    }

    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    public List<Book> search(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return jpaRepository.findAll();
        }

        Specification<Book> spec = null;

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String rawKey = entry.getKey();
            String key = rawKey.toLowerCase();
            String rawValue = entry.getValue();
            if (rawValue == null) continue;

            SearchOperation op = determineOperation(key);
            Object valueObj = parseValueForKey(rawValue.trim(), key);

            SearchCriteria criteria = new SearchCriteria(rawKey, valueObj, op);

            Specification<Book> s = buildSpecFromCriteria(criteria);
            spec = (spec == null) ? s : spec.and(s);
        }

        return spec == null ? jpaRepository.findAll() : jpaRepository.findAll(spec);
    }

    private SearchOperation determineOperation(String key) {
        switch (key) {
            case "visible":
            case "rating":
            case "id":
            case "publicationdate":
            case "stock":
            case "publication_date":
                return SearchOperation.EQUAL;
            default:
                return SearchOperation.MATCH;
        }
    }

    private Object parseValueForKey(String value, String key) {
        try {
            switch (key) {
                case "visible":
                    return Boolean.parseBoolean(value);
                case "rating":
                    return Double.parseDouble(value);
                case "id":
                    return Long.parseLong(value);
                case "stock":
                    return Double.parseDouble(value);
                default:
                    return value;
            }
        } catch (NumberFormatException ex) {
            return value;
        }
    }

    private Specification<Book> buildSpecFromCriteria(SearchCriteria c) {
        return (root, query, cb) -> {
            Object rawValue = c.getValue();
            String field = c.getKey();
            SearchOperation op = c.getOperation();

            try {
                switch (op) {
                    case MATCH:
                        String pattern = "%" + (rawValue == null ? "" : rawValue.toString().toLowerCase()) + "%";
                        return cb.like(cb.lower(root.get(field).as(String.class)), pattern);
                    case EQUAL:
                    default:
                        return cb.equal(root.get(field), rawValue);
                }
            } catch (IllegalArgumentException ex) {
                return cb.conjunction();
            }
        };
    }
}
