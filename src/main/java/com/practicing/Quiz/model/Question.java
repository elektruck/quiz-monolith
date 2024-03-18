package com.practicing.Quiz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String category;
    private Character difficulty;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionTitle;
    private String rightAnswer;

}