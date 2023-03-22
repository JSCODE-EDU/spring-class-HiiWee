package com.jscode.spring.test.repository;

import com.jscode.spring.test.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
