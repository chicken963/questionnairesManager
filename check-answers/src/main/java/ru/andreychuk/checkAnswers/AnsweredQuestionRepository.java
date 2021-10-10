package ru.andreychuk.checkAnswers;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AnsweredQuestionRepository extends CrudRepository<AnsweredQuestion, UUID> {
}
