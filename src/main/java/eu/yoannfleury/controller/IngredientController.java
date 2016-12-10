package eu.yoannfleury.controller;

import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.exception.IngredientNotFoundException;
import eu.yoannfleury.service.IngredientService;
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
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @RequestMapping
    public List<Ingredient> ingredients() {
        return this.ingredientService.getAll();
    }

    @RequestMapping("/{id}")
    public Ingredient read(@PathVariable long id) {
        return this.ingredientService.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        this.ingredientService.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Ingredient create(@Validated @RequestBody Ingredient ingredient) {
        this.ingredientService.exists(ingredient);

        // TODO Check if products are valids and if they already exists
        return this.ingredientService.create(ingredient);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Ingredient update(@PathVariable long id, @Validated @RequestBody Ingredient ingredient) {
        return this.ingredientService.update(id, ingredient);
    }
}
