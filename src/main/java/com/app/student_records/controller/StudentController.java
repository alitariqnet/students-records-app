package com.app.student_records.controller;


import com.app.student_records.entity.Student;
import com.app.student_records.pojo.StudentDto;
import com.app.student_records.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

import static com.app.student_records.mapper.StudentMapper.studentToStudentDto;

@RestController
public class StudentController {


    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto) {
        Student student = studentService.save(studentDto);
        log.info("New student created.");
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/student")
    public ResponseEntity<StudentDto> findStudent(@RequestParam Long id) {
        Optional<Student> student = studentService.find(id);
        if(student.isPresent()) {
            log.info("Student found ", student.get());
            return new ResponseEntity<>(studentToStudentDto(student.get()), HttpStatus.OK);
        }
        else{
            log.warn("Student was not found with id: "+id);
            return new ResponseEntity<>(null, HttpStatus.OK);
            }
    }

    @PostMapping(name = "/api/v1/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String handleUpload(@RequestParam("file") MultipartFile file) throws IOException {
        studentService.store(file);
        log.info("File uploaded successfully");
        return "File uploaded successfully";
    }

    @GetMapping("/")
    public ModelAndView uploadPage(Model model) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("uploadform.html");
        log.info("Sending uploadform.html as view");
        return modelAndView;
    }
}
