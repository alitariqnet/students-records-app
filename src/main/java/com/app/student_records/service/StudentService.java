package com.app.student_records.service;

import com.app.student_records.entity.Student;
import com.app.student_records.pojo.StudentDto;
import com.app.student_records.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private StudentRepository studentRepository;

    public Student save(StudentDto studentDto) {
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setGrade(studentDto.getGrade());
        student.setPhone(studentDto.getPhone());
        student.setBirthDate(LocalDate.parse(studentDto.getBirthDate()));
        log.info("About to create a new student", student);
        return studentRepository.save(student);
    }

    public Optional<Student> find(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> findStudents(String name, String phone, String grade) {
        Specification<Student> nameSpec = null;
        if (null!=name && !name.isEmpty()) {
            nameSpec = (root, query, builder) -> {
                return builder.equal(root.get("name"), name);
            };
        }
        Specification<Student> phoneSpec = null;
        if (null!=phone && !phone.isEmpty()) {
            phoneSpec = (root, query, builder) -> {
                return builder.equal(root.get("phone"), phone);
            };
        }
        Specification<Student> gradeSpec = null;
        if (null!=grade && !grade.isEmpty()) {
            gradeSpec = (root, query, builder) -> {
                return builder.equal(root.get("grade"), grade);
            };
        }
        return studentRepository.findAll(nameSpec.and(phoneSpec.and(gradeSpec)));
    }

    public void store(MultipartFile file) throws IOException {

        String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        String[] records = fileContent.split("\n");
        log.info("Records: ", records);
        List<Student> students = new LinkedList<>();
        for (int i = 0; i < records.length; i++) {
            if (i == 0) continue;
            else {
                String record = records[i];
                String[] values = record.replace("\t", "").replace("\r", "").replace("\n", "").split("\\|");

                Student student = new Student();
                student.setName(values[0].trim());
                student.setPhone(values[1].trim());
                student.setBirthDate(LocalDate.parse(values[2].trim()));
                student.setGrade(values[3].trim());

                log.info("New student record parsed", student);
                students.add(student);
            }
        }
        studentRepository.saveAll(students);
    }

}
