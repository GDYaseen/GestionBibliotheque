
package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
// import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
public class BorrowDAO {

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT b.id, s.id AS student_id, s.name AS student_name, "
                     + "bk.id AS book_id, bk.title, bk.author, bk.publisher, bk.year, bk.is_available,"
                     + "b.borrow_date, b.return_date "
                     + "FROM borrows b "
                     + "JOIN students s ON b.student_id = s.id "
                     + "JOIN books bk ON b.book_id = bk.id";
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setYear(rs.getInt("year"));
                book.setAvailable(rs.getBoolean("is_available"));
                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("book_id"),
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date")
                );
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    public String addBorrow(Borrow borrow) {
        String query = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent());
            stmt.setInt(2, borrow.getBook());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
            System.out.println("Livre emprunté avec succès!");
            return "Livre emprunté avec succès!";
        } catch (SQLException e) {
            System.err.println("Erreur d'ajout borrow: " + e.getMessage());
            return "Erreur d'ajout borrow: " + e.getMessage();
        }
    }

    public String save(Borrow borrow) {
        if (borrow.getId() > 0) {
            return updateBorrow(borrow);
        } else {
            return addBorrow(borrow);
        }
    }

    public String updateBorrow(Borrow borrow) {
        String query = "UPDATE borrows SET student_id = ?, book_id = ?, borrow_date = ?, return_date = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getStudent());
            stmt.setInt(2, borrow.getBook());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.setInt(5, borrow.getId());
            stmt.executeUpdate();
            System.out.println("Borrow record updated successfully.");
            return "Borrow record updated successfully.";
        } catch (SQLException e) {
            System.err.println("Error updating borrow: " + e.getMessage());
            return "Error updating borrow: " + e.getMessage();
        }
    }

    public String stopBorrow(int studentId, int bookId){
        String query = "UPDATE borrows SET return_date = ? WHERE student_id = ? AND book_id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
                 stmt.setDate(1, new java.sql.Date(new Date().getTime()));
            stmt.setInt(2, studentId);
            stmt.setInt(3, bookId);
            stmt.executeUpdate();
            System.out.println("Livre retourné avec succès!");
            return "Livre retourné avec succès!";
        } catch (SQLException e) {
            System.err.println("Error updating borrow: " + e.getMessage());
            return "Error updating borrow: " + e.getMessage();
        }
    }
}
