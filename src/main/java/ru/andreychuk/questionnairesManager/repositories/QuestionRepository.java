package ru.andreychuk.questionnairesManager.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.andreychuk.questionnairesManager.model.Question;
import ru.andreychuk.questionnairesManager.model.Questionnaire;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends CrudRepository<Question, UUID> {
    List<Question> findAllByQuestionAndQuestionnaire(String question, Questionnaire questionnaire);

    List<Question> findAllByQuestionnaireId(UUID questionnaireId);
}
