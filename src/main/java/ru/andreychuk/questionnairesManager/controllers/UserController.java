package ru.andreychuk.questionnairesManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.andreychuk.questionnairesManager.enums.Role;
import ru.andreychuk.questionnairesManager.model.PassedQuestionnaire;
import ru.andreychuk.questionnairesManager.model.User;
import ru.andreychuk.questionnairesManager.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("all")
    public ResponseEntity<List<User>> userList(@AuthenticationPrincipal User currentUser) {
        if (currentUser.isAdmin()) {
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PutMapping("update")
    public ResponseEntity<User> updateUserInfo(@AuthenticationPrincipal User currentUser,
                                               @RequestBody User user) {
        if (currentUser.isAdmin()) {
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

}
