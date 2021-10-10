package ru.andreychuk.questionnairesProvider;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, UUID> {

}
