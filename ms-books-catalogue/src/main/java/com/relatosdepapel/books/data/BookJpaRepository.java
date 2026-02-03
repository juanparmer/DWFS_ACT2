// java
package com.relatosdepapel.books.data;

import com.relatosdepapel.books.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookJpaRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
