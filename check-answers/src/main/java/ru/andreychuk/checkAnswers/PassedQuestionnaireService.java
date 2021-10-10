package ru.andreychuk.checkAnswers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.andreychuk.questionnairesProvider.QuestionnaireService;

import java.util.List;
import java.util.Set;
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
    }

    public Set<PassedQuestionnaire> getUserPassedQuestionnaires(Long userId){
        return passedQuestionnaireRepository.findAllByUserId(userId);
    }
}
