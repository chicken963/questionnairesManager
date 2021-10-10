package ru.andreychuk.questionnairesManager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.andreychuk.questionnairesManager.enums.Role;
import ru.andreychuk.questionnairesManager.model.User;
import ru.andreychuk.questionnairesManager.repositories.UserRepository;
import ru.andreychuk.questionnairesManager.services.UserService;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String provideRegistrationForm(){
        return "registration.html";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        User userFromDb = userService.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return "registration.html";
        }
        user.setRole(Role.USER);
        userService.addUser(user);
        return  "redirect:/login.html";
    }
}
