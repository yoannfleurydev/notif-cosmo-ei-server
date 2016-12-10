package eu.yoannfleury.controller;

import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.exception.IngredientAlreadyExistsException;
import eu.yoannfleury.exception.IngredientNotFoundException;
import eu.yoannfleury.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ingredient controller. This controller is made for a simple CRUD on the ingredients.
 */
@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @RequestMapping
    public List<Ingredient> ingredients() {
        return this.ingredientRepository.findAll();
    }

    @RequestMapping("/{id}")
    public Ingredient user(@PathVariable long id) {
        if (this.ingredientRepository.findOne(id) == null) {
            throw new IngredientNotFoundException(Long.toString(id));
        }

        return this.ingredientRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Ingredient add(@Validated @RequestBody Ingredient ingredient) {
        exists(ingredient);

        // TODO Check if products are valids and if they already exists

        this.ingredientRepository.save(ingredient);

        return this.ingredientRepository.findOneByName(ingredient.getName())
            .orElseThrow(() -> new IngredientNotFoundException(
                    ingredient.getName())
            );
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Ingredient update(@PathVariable long id, @Validated @RequestBody Ingredient ingredient) {
        Ingredient model = this.ingredientRepository.findOne(id);
        if (model != null) {
            model.setName(ingredient.getName());
            model.setProducts(ingredient.getProducts());
            return this.ingredientRepository.saveAndFlush(model);
        }
        return null;
    }

    /**
     * This will check if {@link Ingredient} is unique.
     *
     * @param user The user to create.
     */
    public void exists(Ingredient user) {
        if (this.ingredientRepository.findOneByName(user.getName()).isPresent()) {
            throw new IngredientAlreadyExistsException(user.getName());
        }
    }
}
