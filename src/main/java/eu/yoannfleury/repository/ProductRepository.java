package eu.yoannfleury.repository;

import eu.yoannfleury.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<User, Long> {
}
