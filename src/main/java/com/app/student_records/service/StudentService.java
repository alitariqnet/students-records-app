package com.app.student_records.service;

import com.app.student_records.entity.Student;
import com.app.student_records.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student save(String name, String phone, String birth_date, String grade){
        Student student = new Student();
        student.setName(name);
        student.setGrade(grade);
        student.setPhone(phone);
        student.setBirth_date(birth_date);
        return studentRepository.save(student);
    }
}
