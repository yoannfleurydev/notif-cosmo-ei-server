package eu.yoannfleury.repository;


import eu.yoannfleury.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    public Optional<Ingredient> findOneByName(String name);

    @Query(value = "SELECT ing FROM Ingredient ing WHERE ing.name LIKE :pattern")
    public List<Ingredient> findByPattern(@Param("pattern") String pattern);
}