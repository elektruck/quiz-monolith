package com.practicing.Quiz.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="question")
public class Question {

    public enum DifficultyLevel {
        EASY(1),
        MEDIUM(2),
        HARD(3);

        public final int difficultyLevel;

        private DifficultyLevel(int d) {
            this.difficultyLevel = d;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    private String category;

    @Enumerated(EnumType.ORDINAL)
    private DifficultyLevel difficultyLevel;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String questionTitle;
    private String rightAnswer;

}