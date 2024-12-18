
package com.library.service;

import java.util.Date;
import java.util.List;

import com.library.dao.BookDAO;
// import com.library.dao.StudentDAO;
// import com.library.model.Student;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.model.Book;

public class BorrowService {

    private BorrowDAO borrowDAO;
    private BookDAO bookDAO;

    // Constructeur avec BorrowDAO
    public BorrowService(BorrowDAO borrowDAO,BookDAO bookDAO) {
        this.borrowDAO = borrowDAO;
        this.bookDAO = bookDAO;
    }

    // Méthode pour emprunter un livre
    public String borrowBook(int studentId,int bookId) {
        Book b = bookDAO.getBookById(bookId);
        if(b!=null && !b.isAvailable())
            return "Le livre n'est pas disponible.";
        Student s = new StudentDAO().getStudentById(studentId);
        if(s==null || b==null)
            return "Étudiant ou livre non trouvé.";
        // Sauvegarde de l'emprunt dans la base de données
        Borrow borrow = new Borrow(studentId, bookId, new Date(), null);
        bookDAO.markAvailable(bookId,false);
        System.out.println("From borrowBook: "+studentId+"    "+bookId);
        return borrowDAO.save(borrow);
    }

    public String returnBook(int studentId,int bookId) {
        Book b = bookDAO.getBookById(bookId);
        if(b!=null && b.isAvailable())
            return "Le livre n'est pas emprunté.";
        Student s = new StudentDAO().getStudentById(studentId);
        if(s==null || b==null)
            return "Étudiant ou livre non trouvé.";
        // Sauvegarde de l'emprunt dans la base de données
        bookDAO.markAvailable(bookId,true);
        return borrowDAO.stopBorrow(studentId, bookId);
    }

    // Afficher les emprunts (méthode fictive, à adapter)
    public void displayBorrows() {
        System.out.println("Liste des emprunts...");
        // Afficher les emprunts enregistrés (adapté selon votre DAO)
        List<Borrow> borrows = borrowDAO.getAllBorrows();
        for (Borrow borrow : borrows) {
            System.out.println("ID: " + borrow.getId() + " | Student: " + borrow.getStudent()+ " | Book: " + borrow.getBook()+ " | Borrow Date: " + borrow.getBorrowDate()+ " | Return Date: " + borrow.getReturnDate());
        }
    }
}
