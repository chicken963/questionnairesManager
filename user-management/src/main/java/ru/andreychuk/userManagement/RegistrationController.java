package ru.andreychuk.userManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String provideRegistrationForm(){
        return "registration.html";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return "registration.html";
        }

        userRepository.save(user);
        return  "redirect:/login.html";
    }
}
