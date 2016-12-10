package eu.yoannfleury.repository;

import eu.yoannfleury.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findOneByName(String name);
}
