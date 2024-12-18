package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        bookService = new BookService(bookDAO);
    }

    @Test
    void testAddBook() {
        Book book = new Book("Effective Java", "Joshua Bloch", "123456", 2017);
        bookService.addBook(book);
        int id = book.getId();
        assertEquals("Effective Java", bookDAO.getBookById(id).getTitle());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book("Java Programming", "John Doe", "Van damm",2009);
        bookService.addBook(book);
        book.setAuthor("Jane Doe");
        book.setTitle("Advanced Java");
        bookService.updateBook(book);
        int id = book.getId();
        assertEquals("Advanced Java", bookDAO.getBookById(id).getTitle());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book("Java Programming", "John Doe","Studios Inc.",2023);
        bookService.addBook(book);
        bookService.deleteBook(book.getId());
        assertTrue(bookDAO.getBookById(book.getId())==null);
    }
}
