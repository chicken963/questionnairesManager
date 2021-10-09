package ru.andreychuk.questionnairesManager.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.andreychuk.questionnairesManager.model.AnsweredQuestion;

import java.util.UUID;

public interface AnsweredQuestionRepository extends CrudRepository<AnsweredQuestion, UUID> {
}
