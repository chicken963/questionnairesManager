package ru.andreychuk.questionnairesManager.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.andreychuk.questionnairesManager.model.Questionnaire;

import java.util.UUID;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, UUID> {

}
