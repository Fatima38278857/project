package com.students.project.service;


import com.students.project.exception.StudentNotFoundException;
import com.students.project.model.Faculty;
import com.students.project.model.Student;
import com.students.project.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student create(Student student) {
        return studentRepository.save(student);
    }


    public Student read(long id) {
        return studentRepository.findById(id).orElseThrow(() ->
                new StudentNotFoundException("Такого студента нет"));

    }

    public Student update(Student student) {
        return studentRepository.save(student);
    }

    public void delete(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> findByAllAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> allStudentInformation() {
        return studentRepository.studentAll();
    }

    public Faculty getFacultyById(Long id) {
        return studentRepository.findById(id).get().getFaculty();
    }

    public List<Student> getByFacultyId(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }

    public Integer getAllStudentsNumber() {
        return studentRepository.getAllStudentsNumber();
    }

    public Double getAvg() {
        return studentRepository.getAvg();
    }

    public List<Student> getFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getNamesAlphabeticalOrderUppercase() {
        return studentRepository.findAll().stream()
                .map(n -> n.getName().toUpperCase()).sorted().collect(Collectors.toList());
    }

    public double getAverageAgeOfStudents() {
        IntSummaryStatistics stats = studentRepository.findAll().stream().mapToInt(Student::getAge)
                .summaryStatistics();
        return stats.getAverage();

    }
    public void printThread() {
        List<Student> students = studentRepository.findAll();

        printStudent(students.get(0));
        printStudent(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });
        thread2.start();

        System.out.println();
    }

    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();

        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        });
        thread2.start();

        System.out.println();
    }

    private void printStudent(Student student) {
        System.out.println(Thread.currentThread().getName() + " " + student.getName());
    }

    private synchronized void printStudentSync(Student student) {
        printStudent(student);
    }

}

