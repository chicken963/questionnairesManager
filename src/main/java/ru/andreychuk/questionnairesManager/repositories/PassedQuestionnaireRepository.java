package ru.andreychuk.questionnairesManager.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.andreychuk.questionnairesManager.model.PassedQuestionnaire;

import java.util.UUID;

public interface PassedQuestionnaireRepository extends CrudRepository<PassedQuestionnaire, UUID> {

}
