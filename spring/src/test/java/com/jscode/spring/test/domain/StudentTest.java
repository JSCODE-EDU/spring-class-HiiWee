package com.jscode.spring.test.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.jscode.spring.test.repository.StudentRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudentTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    EntityManager entityManager;

    @DisplayName("Student의 studentClass 칼럼이 null이라면 디폴트값이 생성 된다.")
    @Test
    void generate_column_withDefaultValue() {
        Student student = Student.builder()
                .name("호석")
                .build();

        Student savedStudent = studentRepository.save(student);
        entityManager.flush();
        entityManager.clear();
        Student findStudent = studentRepository.findById(savedStudent.getId()).get();

        Assertions.assertAll(
                () -> assertThat(findStudent.getId()).isNotNull(),
                () -> assertThat(findStudent.getName()).isEqualTo("호석"),
                () -> assertThat(findStudent.getStudentClass()).isEqualTo("basic")
        );
    }

}