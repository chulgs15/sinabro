package com.example.querydsl.repository;

import com.example.querydsl.domain.Book;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class BasicTest {

    @Autowired
    private BookRepositorySupport bookDsl;

    @Autowired
    private BookRepository bookRepo;


    @Test
    @Transactional
    public void test() {
        bookRepo.deleteAll();

        Book book1 = Book.builder()
                .bookName("hello")
                .isbn("book1")
                .build();

        Book book2 = Book.builder()
                .bookName("world")
                .isbn("book2")
                .build();

        bookRepo.save(book1);
        bookRepo.save(book2);
        bookRepo.flush();

        List<Book> books = bookDsl.findAll();
        Book newBook1 = books.get(0);

        Assertions.assertTrue(book1 == newBook1);
    }


}
