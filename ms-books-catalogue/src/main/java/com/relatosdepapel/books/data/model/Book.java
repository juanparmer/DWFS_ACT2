package com.relatosdepapel.books.data.model;

import com.relatosdepapel.books.controller.model.BookDto;
import com.relatosdepapel.books.data.utils.Consts;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Consts.TITLE,nullable = false)
    private String title;

    @Column(name = Consts.AUTHOR,nullable = false)
    private String author;

    @Column(name = Consts.PUBLICATION_DATE)
    private LocalDate publicationDate;

    @Column(name = Consts.CATEGORY)
    private String category;

    @Column(name = Consts.ISBN,unique = true)
    private String isbn;

    @Column(name = Consts.RATING)
    private Integer rating;

    @Column(name= Consts.VISIBLE, nullable = false)
    private Boolean visible = true;

    public void updateFromDto(BookDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.publicationDate = dto.getPublicationDate();
        this.category = dto.getCategory();
        this.isbn = dto.getIsbn();
        this.rating = dto.getRating();
        this.visible = dto.getVisible();
    }

    public void patchFromDto(BookDto dto) {
        if (dto.getTitle() != null) {
            this.title = dto.getTitle();
        }
        if (dto.getAuthor() != null) {
            this.author = dto.getAuthor();
        }
        if (dto.getPublicationDate() != null) {
            this.publicationDate = dto.getPublicationDate();
        }
        if (dto.getCategory() != null) {
            this.category = dto.getCategory();
        }
        if (dto.getIsbn() != null) {
            this.isbn = dto.getIsbn();
        }
        if (dto.getRating() != null) {
            this.rating = dto.getRating();
        }
        if (dto.getVisible() != null) {
            this.visible = dto.getVisible();
        }
    }



}
