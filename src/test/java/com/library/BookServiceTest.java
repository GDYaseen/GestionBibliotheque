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
        Book book = new Book("Java Programming", "John Doe");
        bookService.addBook(book);
        assertEquals("Java Programming", bookDAO.getBookById(1).getTitle());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(1,"Java Programming", "John Doe", "Van damm",2009);
        bookService.addBook(book);
        book.setAuthor("Jane Doe");
        book.setTitle("Advanced Java");
        bookService.updateBook(book);
        assertEquals("Advanced Java", bookDAO.getBookById(1).getTitle());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book(9999,"Java Programming", "John Doe","Studios Inc.",2023);
        bookService.addBook(book);
        bookService.deleteBook(9999);
        assertTrue(bookDAO.getBookById(9999)==null);
    }
}
