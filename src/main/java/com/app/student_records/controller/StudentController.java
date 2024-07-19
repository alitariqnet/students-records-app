package com.app.student_records.controller;


import com.app.student_records.entity.Student;
import com.app.student_records.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestParam String name, @RequestParam String phone, @RequestParam String birth_date, @RequestParam String grade){
        Student student = studentService.save(name,phone,birth_date,grade);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
