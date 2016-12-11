package eu.yoannfleury.repository;

import eu.yoannfleury.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUserName(String userName);

    Optional<User> findOneByEmail(String email);
}
