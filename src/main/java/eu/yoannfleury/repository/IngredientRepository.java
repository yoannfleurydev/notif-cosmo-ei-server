package eu.yoannfleury.repository;


import eu.yoannfleury.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    public Optional<Ingredient> findOneByName(String name);
}