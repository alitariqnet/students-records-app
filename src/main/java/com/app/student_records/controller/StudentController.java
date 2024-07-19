package com.app.student_records.controller;


import com.app.student_records.entity.Student;
import com.app.student_records.pojo.StudentDto;
import com.app.student_records.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class StudentController {


    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto){
        Student student = studentService.save(studentDto);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<Student> findStudent(@RequestParam Long id){
        Student student = studentService.find(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping(name = "/api/v1/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String handleUpload(@RequestParam("file") MultipartFile file) throws IOException {
        studentService.store(file);
        return "File upload successfully";
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "uploadForm";
    }
}
