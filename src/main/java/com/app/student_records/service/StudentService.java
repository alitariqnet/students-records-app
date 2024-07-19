package com.app.student_records.service;

import com.app.student_records.entity.Student;
import com.app.student_records.pojo.StudentDto;
import com.app.student_records.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student save(StudentDto studentDto){
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setGrade(studentDto.getGrade());
        student.setPhone(studentDto.getPhone());
        student.setBirthDate(LocalDate.parse(studentDto.getBirthDate()));
        return studentRepository.save(student);
    }

    public Student find(Long id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            return student.get();
        }
        return student.get();
    }
    public void store(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();
    }
}
