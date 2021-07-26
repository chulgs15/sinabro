package com.example.querydsl.repository;


import com.example.querydsl.domain.Book;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.querydsl.domain.QBook.book;

@Repository
public class BookRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public BookRepositorySupport(JPAQueryFactory queryFactory) {
        super(Book.class);
        this.queryFactory = queryFactory;
    }

    public List<Book> findAll() {
        return queryFactory.selectFrom(book)
                .orderBy(book.bookId.asc()).fetch();
    }

    public List<Book> findByName(String bookName) {
        return queryFactory
                .selectFrom(book)
                .orderBy(book.bookId.asc())
                .fetch()
                ;
    }

}
