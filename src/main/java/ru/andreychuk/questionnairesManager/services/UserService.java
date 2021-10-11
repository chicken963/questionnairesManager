package ru.andreychuk.questionnairesManager.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.andreychuk.questionnairesManager.model.User;
import ru.andreychuk.questionnairesManager.repositories.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findById(user.getId()).orElse(null);
        if (userFromDb != null) {
            if (user.getPassword().isEmpty()) {
                log.warn("Error while saving data for user {}: Password should not be empty!", user.getUsername());
                return false;
            }
            userFromDb.setPassword(user.getPassword());
            userFromDb.setUsername(user.getUsername());
            userFromDb.setRole(user.getRole());
            userRepository.save(userFromDb);
        } else {
            userRepository.save(user);
        }
        return true;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
