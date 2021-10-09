package ru.andreychuk.questionnairesManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.andreychuk.questionnairesManager.model.PassedQuestionnaire;
import ru.andreychuk.questionnairesManager.model.Questionnaire;
import ru.andreychuk.questionnairesManager.model.User;
import ru.andreychuk.questionnairesManager.services.PassedQuestionnaireService;
import ru.andreychuk.questionnairesManager.services.QuestionnaireService;

import java.util.List;
import java.util.UUID;

@Controller
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private PassedQuestionnaireService passedQuestionnaireService;

    @PostMapping("/new")
    public ResponseEntity<Questionnaire> provideCreateForm(@AuthenticationPrincipal User user,
                                                    @RequestBody Questionnaire questionnaire) {
        if (user.isAdmin()) {
            questionnaireService.saveQuestionnaire(questionnaire);
        }
        return ResponseEntity.ok(questionnaire);
    }

    @GetMapping("/view")
    public String startPage(@AuthenticationPrincipal User user) {
        if (user.isAdmin()) {
            return "view.html";
        } else {
            return "view_user.html";
        }
    }


    @GetMapping("/questionnaires")
    public ResponseEntity<List<Questionnaire>> getAllQuestionnaires() {
        return ResponseEntity.ok(questionnaireService.getAllQuestionnaires());
    }


    @DeleteMapping("/questionnaires/{id}")
    public ResponseEntity<Questionnaire> deleteQuestionnaire(@PathVariable String id) {
        UUID questionnaireId = UUID.fromString(id);
        return ResponseEntity.ok(questionnaireService.delete(questionnaireId));
    }


    @PostMapping("/send")
    public ResponseEntity<PassedQuestionnaire> storeNewResult(@AuthenticationPrincipal User user,
                                                              @RequestBody PassedQuestionnaire questionnaire) {
        questionnaire.setUserId(user.getId());
        passedQuestionnaireService.savePassedQuestionnaire(questionnaire);
        return ResponseEntity.ok(questionnaire);
    }

    @PutMapping("/update")
    public ResponseEntity<Questionnaire> editQuestionnaire(@AuthenticationPrincipal User user,
                                    @RequestBody Questionnaire questionnaire) {
        if (user.isAdmin()) {
            questionnaireService.updateQuestionnaire(questionnaire);
        }
        return ResponseEntity.ok(questionnaire);
    }
}
