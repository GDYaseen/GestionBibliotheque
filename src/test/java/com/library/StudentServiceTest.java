package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
        Student student = new Student("Alice");
        studentService.addStudent(student);
        int id = student.getId();
        // assertEquals(1, studentDAO.getAllStudents().size());
        assertEquals("Alice", studentDAO.getStudentById(id).getName());
    }

    @Test
    void testUpdateStudent() {
        Student alice = new Student("Alice");
        studentService.addStudent(alice);
        alice.setName("Alice Smith");
        studentService.updateStudent(alice);
        int id = alice.getId();
        assertEquals("Alice Smith", studentDAO.getStudentById(id).getName());
    }

    @Test
    void testDeleteStudent() {
        Student alice = new Student( "Alice");
        studentService.addStudent(alice);
        int id = alice.getId();
        studentService.deleteStudent(id);
        assertTrue(studentDAO.getStudentById(id)==null);
    }

    @Test
    void testGetAllStudents() {
        Student alice = new Student("Alice");
        studentService.addStudent(alice);
        Student bob = new Student("Bob");
        studentService.addStudent(bob);
        List<Student> students = studentDAO.getAllStudents();
        assertTrue(students.stream().anyMatch(s -> s.getId() == alice.getId() && s.getName().equals("Alice")),
               "Alice is not in the list or her ID is incorrect");
        assertTrue(students.stream().anyMatch(s -> s.getId() == bob.getId() && s.getName().equals("Bob")),
               "Bob is not in the list or his ID is incorrect");
    }
}
