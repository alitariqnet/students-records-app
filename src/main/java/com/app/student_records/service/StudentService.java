package com.app.student_records.service;

import com.app.student_records.entity.Student;
import com.app.student_records.pojo.StudentDto;
import com.app.student_records.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        String[] records = fileContent.split("\n");
        System.out.println(records);
        for (int i =0;i<records.length;i++){
            if(i==0) continue;
            else{
                String record = records[i];
//                record = record.replace("\t","");
//                record = record.replace("\n","");
                String[] values = record.replace("\t","").replace("\r","").replace("\n","").replace("|","").replace("  "," ").split(" ");
                Student student = new Student();
                student.setName(values[0].trim());
                student.setPhone(values[1].trim());
                student.setBirthDate(LocalDate.parse(values[2].trim()));
                student.setGrade(values[3].trim());
                studentRepository.save(student);
                }
            }
        }

}
