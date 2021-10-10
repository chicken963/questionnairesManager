package ru.andreychuk.checkAnswers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.andreychuk.userManagement.User;
import ru.andreychuk.userManagement.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/result")
public class ResultController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassedQuestionnaireService passedQuestionnaireService;

    @GetMapping("/all")
    public ResponseEntity<Map<Long, Set<PassedQuestionnaire>>> getAllResults(@AuthenticationPrincipal User currentUser) {
        Map<Long, Set<PassedQuestionnaire>> allResults = new HashMap();
        if (currentUser.isAdmin()) {
            for (User user : userRepository.findAll()) {
                allResults.put(user.getId(), passedQuestionnaireService.getUserPassedQuestionnaires(user.getId()));
            }
        }
        return ResponseEntity.ok(allResults);
    }

    @GetMapping("/{user}")
    public ResponseEntity<Set<PassedQuestionnaire>> getUserResults(@AuthenticationPrincipal User currentUser,
                                                                   @PathVariable User user) {
        if (currentUser.isAdmin()) {
            if (user != null) {
                return ResponseEntity.ok(passedQuestionnaireService.getUserPassedQuestionnaires(user.getId()));
            } else {
                return ResponseEntity.badRequest().body(Collections.emptySet());
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptySet());
    }


    @PostMapping("/send")
    public ResponseEntity<PassedQuestionnaire> storeNewResult(@AuthenticationPrincipal User user,
                                                              @RequestBody PassedQuestionnaire questionnaire) {
        questionnaire.setUserId(user.getId());
        passedQuestionnaireService.savePassedQuestionnaire(questionnaire);
        return ResponseEntity.ok(questionnaire);
    }
}
