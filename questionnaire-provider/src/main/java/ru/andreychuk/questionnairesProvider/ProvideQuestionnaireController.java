package ru.andreychuk.questionnairesProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.andreychuk.userManagement.User;

import java.util.List;

@Controller
public class ProvideQuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

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



}
