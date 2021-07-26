package com.example.querydsl.domain;

import lombok.Builder;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_name")
    private String bookName;


    @Column(name = "isbn")
    private String isbn;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    public Book() {
    }

    @Builder
    public Book(String bookName, String isbn) {
        this.bookName = bookName;
        this.isbn = isbn;
    }


}
