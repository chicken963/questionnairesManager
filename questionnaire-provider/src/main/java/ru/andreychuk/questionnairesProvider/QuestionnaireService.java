package ru.andreychuk.questionnairesProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public void saveQuestionnaire(Questionnaire questionnaire) {
        questionnaireRepository.save(questionnaire);
        for (Question question : questionnaire.getQuestions()) {
            question.setQuestionnaire(questionnaire);
            questionRepository.save(question);
        }
    }

    public List<Questionnaire> getAllQuestionnaires() {
        return (List<Questionnaire>) questionnaireRepository.findAll();
    }

    public Questionnaire getById(UUID id) {
        return questionnaireRepository.findById(id).orElse(null);
    }

    public void updateQuestionnaire(Questionnaire questionnaire) {
        questionnaireRepository.save(questionnaire);
        List<UUID> questionIdsAfterUpdate = questionnaire.getQuestions().stream()
                .map(Question::getId)
                .collect(Collectors.toList());
        List<Question> dbQuestions = questionRepository.findAllByQuestionnaireId(questionnaire.getId());
        dbQuestions.forEach(question -> {
            if (!questionIdsAfterUpdate.contains(question.getId())) {
                questionRepository.delete(question);
            }
        });
    }

    public Questionnaire delete(UUID questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new IllegalArgumentException("Wrong questionnaire id"));
        questionnaireRepository.delete(questionnaire);
        return questionnaire;
    }
}
