package ru.andreychuk.questionnairesManager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.andreychuk.questionnairesManager.model.AnsweredQuestion;
import ru.andreychuk.questionnairesManager.model.PassedQuestionnaire;
import ru.andreychuk.questionnairesManager.repositories.PassedQuestionnaireRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PassedQuestionnaireService {

    @Autowired
    private PassedQuestionnaireRepository passedQuestionnaireRepository;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private AnsweredQuestionsService answeredQuestionsService;

    public void savePassedQuestionnaire(PassedQuestionnaire passedQuestionnaire){
        UUID sourceQuestionnaireId = passedQuestionnaire.getId();
        passedQuestionnaire.setSourceQuestionnaire(questionnaireService.getById(sourceQuestionnaireId));
        passedQuestionnaire.setId(null);
        List<AnsweredQuestion> answeredQuestions = passedQuestionnaire.getQuestions();
        answeredQuestions.forEach((question) -> {
            question.setSourceQuestionId(question.getId());
            question.setId(null);
            question.setPassedQuestionnaire(null);
            answeredQuestionsService.add(question);
            passedQuestionnaire.setSourceQuestionnaire(questionnaireService.getById(sourceQuestionnaireId));
            question.setPassedQuestionnaire(passedQuestionnaire);
        });
        passedQuestionnaireRepository.save(passedQuestionnaire);
//        answeredQuestions.forEach((question) -> answeredQuestionsService.add(question));
    }
}
