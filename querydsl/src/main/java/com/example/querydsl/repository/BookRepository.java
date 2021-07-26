package com.example.querydsl.repository;

import com.example.querydsl.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
