package ru.andreychuk.questionnairesManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.andreychuk.questionnairesManager.model.PassedQuestionnaire;
import ru.andreychuk.questionnairesManager.model.User;
import ru.andreychuk.questionnairesManager.repositories.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/result")
@PreAuthorize("hasAuthority('ADMIN')")
public class ResultController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<Map<Long, Set<PassedQuestionnaire>>> getAllResults() {
        Map<Long, Set<PassedQuestionnaire>> allResults = new HashMap<>();
        for (User user : userRepository.findAll()) {
            allResults.put(user.getId(), user.getPassedQuestionnaires());
        }

        return ResponseEntity.ok(allResults);
    }

    @GetMapping("/{user}")
    public ResponseEntity<Set<PassedQuestionnaire>> getUserResults(@PathVariable User user) {
        if (user != null) {
            return ResponseEntity.ok(user.getPassedQuestionnaires());
        } else {
            return ResponseEntity.badRequest().body(Collections.emptySet());
        }
    }
}
