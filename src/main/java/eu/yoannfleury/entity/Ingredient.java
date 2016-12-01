package eu.yoannfleury.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Ingredient compose a {@link Product}.
 */
@Entity
public class Ingredient {
    /**
     * The index of the ingredient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The name of the ingredient.
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private List<Product> products;

    /**
     * Default constructor of the ingredient.
     */
    public Ingredient(){};

    /**
     * Constructor of ingredient.
     * @param name The name of the ingredient.
     */
    public Ingredient(String name) {
        this.name = name;
    }

    /**
     * Getter for the index of the ingredient.
     * @return The index of the ingredient.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the index of the ingredient.
     * @param id The index of the ingredient.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the name of the ingredient.
     * @return The name of the ingredient.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the ingredient.
     * @param name The name of the ingredient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for all the products in which the ingredient is.
     * @see Product
     * @return A list of products.
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Setter for all the products in which the ingredient is.
     * @param products The list of products where the ingredient is.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
