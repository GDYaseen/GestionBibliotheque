package com.library;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorrowServiceTest {
    private BorrowService borrowService;
    private BookDAO bookDAO;
    private StudentDAO studentDAO;
    private BorrowDAO borrowDAO;
    private Student alice;
    private Book book1;
    private Student bob;
    private Book book2;
    
    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        studentDAO = new StudentDAO();
        borrowDAO = new BorrowDAO();
        borrowService = new BorrowService(borrowDAO,bookDAO);

        // Ajouter un étudiant
        alice = new Student("Alice");
        bob = new Student( "Bob");
        
        studentDAO.addStudent(alice);
        studentDAO.addStudent(bob);

        // Ajouter des livres
        book1 = new Book("Java Programming", "John Doe","Van damm",2009);
        bookDAO.add(book1);
        book2 = new Book("Advanced Java", "Jane Doe","Studio inc.",2023);
        bookDAO.add(book2);
    }

    @Test
    void testBorrowBook() {
        assertEquals("Livre emprunté avec succès!", borrowService.borrowBook(alice.getId(), book1.getId()));
        assertFalse(bookDAO.getBookById(book1.getId()).isAvailable());
    }

    @Test
    void testReturnBook() {
        borrowService.borrowBook(alice.getId(), book1.getId());
        assertEquals("Livre retourné avec succès!", borrowService.returnBook(alice.getId(), book1.getId()));
        assertTrue(bookDAO.getBookById(book1.getId()).isAvailable());
    }

    @Test
    void testBorrowBookNotAvailable() {
        borrowService.borrowBook(bob.getId(), book2.getId());
        assertEquals("Le livre n'est pas disponible.", borrowService.borrowBook(alice.getId(), book2.getId()));
        borrowService.returnBook(bob.getId(), book2.getId());
    }

    @Test
    void testBorrowBookStudentNotFound() {
        assertEquals("Étudiant ou livre non trouvé.", borrowService.borrowBook(3, 1));
    }
}
