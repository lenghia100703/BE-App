package com.mobileapp.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "question")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @OneToOne
    @JoinColumn(name = "answer_id")
    private AnswerEntity answerId;

    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private UserEntity adminId;
}
