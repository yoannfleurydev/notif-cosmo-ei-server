package eu.yoannfleury.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductDTO {
    /**
     * The index of the product.
     */
    private long id;

    /**
     * The name of the product.
     */
    @NotNull
    private String name;

    /**
     * A list of ingredients for the given product.
     */
    private List<Long> ingredients;

    public ProductDTO() {
    }

    /**
     * @param id          The index of the product
     * @param name        The name of the product
     * @param ingredients The list of ingredients of the product.
     */
    public ProductDTO(long id, String name, List<Long> ingredients) {
        this.id = id;
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

    public List<Long> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Long> ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }
}
