package ru.andreychuk.questionnairesProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionnaireService questionnaireService;


    public Question getByNameAndQuestionnaireId(String name, UUID questionnaireId) {
        Questionnaire questionnaire = questionnaireService.getById(questionnaireId);
        return questionRepository.findAllByQuestionAndQuestionnaire(name, questionnaire).get(0);
    }

    public Question getById(UUID id){
        return questionRepository.findById(id).orElse(null);
    }
}
