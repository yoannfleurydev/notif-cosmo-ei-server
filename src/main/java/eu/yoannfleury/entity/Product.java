package eu.yoannfleury.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * A cosmetic product is defined by one or more {@link Ingredient}. It can have
 * side {@link Effect}.
 */
@Entity
public class Product {

    /**
     * The index of the product.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The name of the product.
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    private List<Ingredient> ingredients;

    /**
     * Default constructor
     */
    public Product(){}

    public Product(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }
}
