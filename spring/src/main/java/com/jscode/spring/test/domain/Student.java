package com.jscode.spring.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "class")
    @ColumnDefault("'basic'")
    private String studentClass;

    protected Student() {
    }

    @Builder
    private Student(final Long id, final String name, final String studentClass) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
    }

}
