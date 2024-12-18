package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService studentService;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        studentDAO = new StudentDAO();
        studentService = new StudentService(studentDAO);
    }

    @Test
    void testAddStudent() {
        studentService.addStudent(new Student(1, "Alice"));
        // assertEquals(1, studentDAO.getAllStudents().size());
        assertEquals("Alice", studentDAO.getStudentById(1).getName());
    }

    @Test
    void testUpdateStudent() {
        Student alice = new Student(1, "Alice");
        studentService.addStudent(alice);
        alice.setName("Alice Smith");
        studentService.updateStudent(alice);
        assertEquals("Alice Smith", studentDAO.getStudentById(1).getName());
    }

    @Test
    void testDeleteStudent() {
        Student alice = new Student(1, "Alice");
        studentService.addStudent(alice);
        studentService.deleteStudent(1);
        assertTrue(studentDAO.getStudentById(1)==null);
    }

    @Test
    void testGetAllStudents() {
        Student alice = new Student(1, "Alice");
        studentService.addStudent(alice);
        Student bob = new Student(1, "Bob");
        studentService.addStudent(bob);
        assertEquals(2, studentDAO.getAllStudents().size());
    }
}
