package ru.andreychuk.questionnaireConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.andreychuk.questionnairesProvider.Questionnaire;
import ru.andreychuk.questionnairesProvider.QuestionnaireService;
import ru.andreychuk.userManagement.User;

import java.util.List;
import java.util.UUID;

@Controller
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;


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

    @PutMapping("/update")
    public ResponseEntity<Questionnaire> editQuestionnaire(@AuthenticationPrincipal User user,
                                    @RequestBody Questionnaire questionnaire) {
        if (user.isAdmin()) {
            questionnaireService.updateQuestionnaire(questionnaire);
        }
        return ResponseEntity.ok(questionnaire);
    }
}
