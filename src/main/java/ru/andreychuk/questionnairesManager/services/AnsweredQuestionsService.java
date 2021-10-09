package ru.andreychuk.questionnairesManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.andreychuk.questionnairesManager.model.AnsweredQuestion;
import ru.andreychuk.questionnairesManager.repositories.AnsweredQuestionRepository;

@Service
public class AnsweredQuestionsService {
    @Autowired
    private AnsweredQuestionRepository answeredQuestionRepository;

    public void add(AnsweredQuestion answeredQuestion) {
        answeredQuestionRepository.save(answeredQuestion);
    }

}
