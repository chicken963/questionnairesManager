package ru.andreychuk.checkAnswers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnsweredQuestionsService {
    @Autowired
    private AnsweredQuestionRepository answeredQuestionRepository;

    public void add(AnsweredQuestion answeredQuestion) {
        answeredQuestionRepository.save(answeredQuestion);
    }

}
