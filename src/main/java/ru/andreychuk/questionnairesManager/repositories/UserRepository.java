package ru.andreychuk.questionnairesManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.andreychuk.questionnairesManager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
