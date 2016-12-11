package eu.yoannfleury.dto;

import eu.yoannfleury.entity.Product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class IngredientDTO {
    /**
     * The index of the ingredient.
     */
    private long id;

    /**
     * The name of the ingredient.
     */
    @NotNull(message = "error.name.notnull")
    @Size(min = 1, message = "error.name.size")
    private String name;

    private List<Product> products;

    public IngredientDTO() {
    }

    public IngredientDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter for the index of the ingredient.
     *
     * @return The index of the ingredient.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the index of the ingredient.
     *
     * @param id The index of the ingredient.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the name of the ingredient.
     *
     * @return The name of the ingredient.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the ingredient.
     *
     * @param name The name of the ingredient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for all the products in which the ingredient is.
     *
     * @return A list of products.
     * @see Product
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Setter for all the products in which the ingredient is.
     *
     * @param products The list of products where the ingredient is.
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
