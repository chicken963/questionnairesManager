package ru.andreychuk.questionnairesProvider;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends CrudRepository<Question, UUID> {
    List<Question> findAllByQuestionAndQuestionnaire(String question, Questionnaire questionnaire);

    List<Question> findAllByQuestionnaireId(UUID questionnaireId);
}
