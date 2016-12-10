package eu.yoannfleury.service;

import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.exception.IngredientAlreadyExistsException;
import eu.yoannfleury.exception.IngredientNotFoundException;
import eu.yoannfleury.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    /**
     * The {@link Ingredient} repository.
     */
    private final IngredientRepository ingredientRepository;

    /**
     * Constructor for {@link IngredientService}.
     * @param ingredientRepository The {@link Ingredient} entity repository.
     */
    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Get one ingredient based on the index parameter.
     * @param id The index of the {@link Ingredient} to fetch.
     * @return The ingredient that matches with the index parameter.
     */
    public Ingredient get(long id) {
        if (this.ingredientRepository.findOne(id) == null) {
            throw new IngredientNotFoundException(Long.toString(id));
        }

        return this.ingredientRepository.findOne(id);
    }

    /**
     *
     * @return The list of all the ingredients
     */
    public List<Ingredient> getAll() {
        return this.ingredientRepository.findAll();
    }

    /**
     *
     * @param ingredient The ingredient to create.
     * @return The ingredient newly created
     */
    public Ingredient create(Ingredient ingredient) {
        this.ingredientRepository.save(ingredient);

        return this.ingredientRepository.findOneByName(ingredient.getName())
            .orElseThrow(() -> new IngredientNotFoundException(
                    ingredient.getName())
            );
    }

    /**
     *
     * @param id The index of the ingredient you want to create.
     * @param ingredient The model with the new data.
     * @return The ingredient that matches with the parameter index, with the new values.
     */
    public Ingredient update(long id, Ingredient ingredient) {
        Ingredient entity = this.ingredientRepository.findOne(id);
        if (entity == null) {
            throw new IngredientNotFoundException(id);
        }

        entity.setName(ingredient.getName());
        entity.setProducts(ingredient.getProducts());

        return this.ingredientRepository.saveAndFlush(entity);
    }

    public void delete(long id) {
        if (this.ingredientRepository.findOne(id) == null) {
            throw new IngredientNotFoundException(id);
        }
        this.ingredientRepository.delete(id);
    }

    /**
     * This will check if {@link Ingredient} is unique.
     *
     * @param ingredient The user to create.
     */
    public void exists(Ingredient ingredient) {
        if (this.ingredientRepository.findOneByName(ingredient.getName()).isPresent()) {
            throw new IngredientAlreadyExistsException(ingredient.getName());
        }
    }
}
