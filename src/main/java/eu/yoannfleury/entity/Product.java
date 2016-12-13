package eu.yoannfleury.entity;

import org.aspectj.weaver.ast.Not;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
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

    @ManyToMany(mappedBy = "products")
    private List<Notification> notifications;

    /**
     * Default constructor
     */
    public Product() {
        this.ingredients = new LinkedList<>();
    }

    public Product(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
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

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
