package ru.andreychuk.questionnairesManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.andreychuk.questionnairesManager.model.PassedQuestionnaire;
import ru.andreychuk.questionnairesManager.model.Questionnaire;
import ru.andreychuk.questionnairesManager.model.User;
import ru.andreychuk.questionnairesManager.services.PassedQuestionnaireService;
import ru.andreychuk.questionnairesManager.services.QuestionnaireService;

import java.util.List;

@Controller
public class ViewQuestionnairesController {

    @Autowired
    private PassedQuestionnaireService passedQuestionnaireService;

    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping("/questionnaires")
    public ResponseEntity<List<Questionnaire>> getAllQuestionnaires() {
        return ResponseEntity.ok(questionnaireService.getAllQuestionnaires());
    }

    @GetMapping("/view")
    public String startPage(@AuthenticationPrincipal User user) {
        if (user.isAdmin()) {
            return "view.html";
        } else {
            return "view_user.html";
        }
    }

    @PostMapping("/send")
    public ResponseEntity<PassedQuestionnaire> storeNewResult(@AuthenticationPrincipal User user,
                                                              @RequestBody PassedQuestionnaire questionnaire) {
        questionnaire.setUserId(user.getId());
        passedQuestionnaireService.savePassedQuestionnaire(questionnaire);
        return ResponseEntity.ok(questionnaire);
    }
}
