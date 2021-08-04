package ormNext.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ormNext.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}
