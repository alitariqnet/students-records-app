package com.app.student_records.mapper;

import com.app.student_records.entity.Student;
import com.app.student_records.pojo.StudentDto;

import java.time.LocalDate;

public class StudentMapper {

    public static Student studentDtoToStudent(StudentDto studentDto){
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setPhone(studentDto.getPhone());
        student.setBirthDate(LocalDate.parse(studentDto.getBirthDate()));
        student.setGrade(studentDto.getGrade());
        return student;
    }

    public static StudentDto studentToStudentDto(Student student){
        StudentDto studentDto = new StudentDto();
        studentDto.setName(student.getName());
        studentDto.setPhone(student.getPhone());
        studentDto.setBirthDate(student.getBirthDate().toString());
        studentDto.setGrade(student.getGrade());
        return studentDto;
    }
}
