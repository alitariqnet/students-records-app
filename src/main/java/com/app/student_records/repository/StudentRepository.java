package com.app.student_records.repository;

import com.app.student_records.entity.Student;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepositoryImplementation<Student, Long> {
}