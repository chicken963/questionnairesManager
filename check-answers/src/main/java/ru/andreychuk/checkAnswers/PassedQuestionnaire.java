package ru.andreychuk.checkAnswers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.andreychuk.questionnairesProvider.Questionnaire;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questionnaire_results")
@Setter
@Getter
public class PassedQuestionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id")
    private long userId;

    @Column(name="heading")
    private String heading;

    @OneToMany(mappedBy = "passedQuestionnaire",
            targetEntity = AnsweredQuestion.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<AnsweredQuestion> questions;

    @ManyToOne(targetEntity = Questionnaire.class, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @JoinColumn(name = "source_id")
    private Questionnaire sourceQuestionnaire;

}
