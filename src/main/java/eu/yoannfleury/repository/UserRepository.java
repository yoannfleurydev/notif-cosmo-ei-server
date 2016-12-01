package eu.yoannfleury.repository;

import eu.yoannfleury.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<List<User>> findByFirstName(String firstName);
    Optional<User> findOneByUserName(String userName);
    Optional<User> findOneByEmail(String email);
}
