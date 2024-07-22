package com.app.student_records.service;

import com.app.student_records.entity.Student;
import com.app.student_records.pojo.StudentDto;
import com.app.student_records.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(StudentDto studentDto) {
        Student student = null;
        try {
            student = new Student();
            student.setName(studentDto.getName());
            student.setGrade(studentDto.getGrade());
            student.setPhone(studentDto.getPhone());
            student.setBirthDate(LocalDate.parse(studentDto.getBirthDate()));
            log.info("About to create a new student", student);
        } catch (Exception e) {
            log.error("Error occured while saving a new student record." + e.getMessage());
        }
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
        List<Student> students = new LinkedList<>();
        if(nameSpec != null && phoneSpec == null && gradeSpec == null){
            students = studentRepository.findAll(nameSpec);
        }
        else if(nameSpec != null && phoneSpec != null && gradeSpec == null){
            students = studentRepository.findAll(nameSpec.and(phoneSpec));
        }
        else if(nameSpec != null && phoneSpec != null && gradeSpec != null){
            students = studentRepository.findAll(nameSpec.and(phoneSpec.and(gradeSpec)));
        }
        else if(nameSpec != null && phoneSpec == null && gradeSpec != null){
            students = studentRepository.findAll(nameSpec.and(gradeSpec));
        }
        else if(nameSpec == null && phoneSpec != null && gradeSpec == null){
            students = studentRepository.findAll(phoneSpec);
        }
        else if(nameSpec == null && phoneSpec == null && gradeSpec != null){
            students = studentRepository.findAll(gradeSpec);
        }
        else if(nameSpec == null && phoneSpec != null && gradeSpec != null){
            students = studentRepository.findAll(phoneSpec.and(gradeSpec));
        }
        else if(nameSpec != null && phoneSpec == null && gradeSpec != null){
            students = studentRepository.findAll(nameSpec.and(gradeSpec));
        }
        return students;
    }

    public void store(MultipartFile file) throws IOException {

        try {
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
            String[] records = fileContent.split("\n");
            List<Student> students = new LinkedList<>();

            for (int i = 0; i < records.length; i++) {
                if (i == 0)
                    continue;
                else {
                    String record = records[i];
                    String[] values = record.replace("\t", "").replace("\r", "").replace("\n", "").split("\\|");

                    Student student = new Student();
                    student.setName(values[0].trim());
                    student.setPhone(values[1].trim());
                    student.setBirthDate(LocalDate.parse(values[2].trim()));
                    student.setGrade(values[3].trim());

                    log.info("New student record parsed " + student.toString());
                    students.add(student);
                }
            }
            studentRepository.saveAll(students);
        }catch(Exception e){
            log.error("Error occured during parsing file"+e.getMessage());
        }
    }

}
