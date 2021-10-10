package ru.andreychuk.checkAnswers;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface PassedQuestionnaireRepository extends CrudRepository<PassedQuestionnaire, UUID> {
    public Set<PassedQuestionnaire> findAllByUserId(Long userId);
}
