package ru.andreychuk.questionnairesManager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "questionnaires")
@Setter
@Getter
@Accessors(chain = true)
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "questionnaire",
            targetEntity = Question.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Question> questions;

    private String heading;
}
