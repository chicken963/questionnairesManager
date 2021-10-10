package ru.andreychuk.questionnairesManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasAuthority('ADMIN')")
public class EditQuestionnairesController {
    @Autowired
    private QuestionnaireService questionnaireService;


    @PostMapping("/new")
    public ResponseEntity<Questionnaire> provideCreateForm(@RequestBody Questionnaire questionnaire) {
        questionnaireService.saveQuestionnaire(questionnaire);
        return ResponseEntity.ok(questionnaire);
    }


    @DeleteMapping("/questionnaires/{id}")
    public ResponseEntity<Questionnaire> deleteQuestionnaire(@PathVariable String id) {
        UUID questionnaireId = UUID.fromString(id);
        return ResponseEntity.ok(questionnaireService.delete(questionnaireId));
    }


    @PutMapping("/update")
    public ResponseEntity<Questionnaire> editQuestionnaire(@RequestBody Questionnaire questionnaire) {

        questionnaireService.updateQuestionnaire(questionnaire);
        return ResponseEntity.ok(questionnaire);
    }
}
