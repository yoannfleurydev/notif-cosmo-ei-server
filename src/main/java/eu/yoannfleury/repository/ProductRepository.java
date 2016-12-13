package eu.yoannfleury.repository;

import eu.yoannfleury.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findOneByName(String name);

    @Query("SELECT pro FROM Product pro WHERE pro.name LIKE :pattern")
    public List<Product> findByPattern(@Param("pattern") String pattern);
}
