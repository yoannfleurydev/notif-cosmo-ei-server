package eu.yoannfleury.controller;

import eu.yoannfleury.dto.IngredientDTO;
import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
    public List<IngredientDTO> ingredients(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "property", defaultValue = "id") String property
    ) {
        return this.ingredientService.getByPagination(page, limit, direction, property);
    }

    @RequestMapping("/search")
    public List<IngredientDTO> search(
            @RequestParam(value = "value", defaultValue = "") String value) {
        return this.ingredientService.search(value);
    }

    @RequestMapping("/{id}")
    public IngredientDTO read(@PathVariable long id) {
        return this.ingredientService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public IngredientDTO create(@Validated @RequestBody IngredientDTO ingredient) {
        this.ingredientService.exists(ingredient);

        return this.ingredientService.create(ingredient);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public IngredientDTO update(@PathVariable long id, @Validated @RequestBody IngredientDTO ingredient) {
        return this.ingredientService.update(id, ingredient);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.ingredientService.delete(id);
    }
}
