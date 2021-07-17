package com.example.task1crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp CreatedDate;

    @Column(updatable = false, nullable = false)
    @UpdateTimestamp
    private Timestamp updateTime;

    private String text;
    private String title;

    @CreatedBy
    @ManyToOne
    private User users;

}
