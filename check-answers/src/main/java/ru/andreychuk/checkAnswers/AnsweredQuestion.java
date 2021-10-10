package ru.andreychuk.checkAnswers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "answered_questions")
@Setter
@Getter
public class AnsweredQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(targetEntity = PassedQuestionnaire.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @JoinColumn(name = "answered_questionnaire_id")
    private PassedQuestionnaire passedQuestionnaire;

//    @ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @JsonBackReference
    @Column(name = "source_id")
    private UUID sourceQuestionId;

    @Column(name = "answers")
    private String answers;

    @Column(name = "rejected_answers")
    private String rejectedAnswers;

    @Column(name = "question")
    private String question;

}
