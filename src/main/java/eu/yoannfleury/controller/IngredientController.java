package eu.yoannfleury.controller;

import eu.yoannfleury.dto.IngredientDTO;
import eu.yoannfleury.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Ingredient controller. This controller is made for a simple CRUD on the ingredients.
 */
@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    /**
     * The constructor of the ingredients controller.
     * @param ingredientService Ingredient service to get data. Auto inject in
     *                          the constructor
     */
    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * The method matching route <code>/ingredients</code>. This method
     * will return all the ingredients, using a pagination system.
     * @param page The page you want to get.
     * @param limit The number of item per page.
     * @param direction The direction, ascending or descending.
     * @param property The property on witch to apply the previous filters.
     * @param httpServletResponse The response object used to set a header to
     *                            indicate the number of pages.
     * @return A list of {@link IngredientDTO}.
     */
    @RequestMapping
    public List<IngredientDTO> ingredients(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "property", defaultValue = "id") String property,
            HttpServletResponse httpServletResponse
    ) {
        Integer pages = (int) Math.ceil((double)this.ingredientService.getAll().size() / (double)limit);
        httpServletResponse.setHeader("X-Pages", pages.toString());
        return this.ingredientService.getByPagination(page, limit, direction, property);
    }

    /**
     * The method matching route <code>/ingredients/search?value=example</code>.
     * This method will return all the ingredients that match the string pass through
     * the request parameter in their description.
     * @param value The string to match in the description.
     * @return A list of {@link IngredientDTO} that match the value.
     */
    @RequestMapping("/search")
    public List<IngredientDTO> search(
            @RequestParam(value = "value", defaultValue = "") String value) {
        return this.ingredientService.search(value);
    }

    /**
     * The method matching route <code>/ingredients/{id}</code>. This
     * method will return the ingredient that match the id path variable.
     * @param id The index of the effect you want to get.
     * @return The {@link IngredientDTO} that match the path variable.
     */
    @RequestMapping("/{id}")
    public IngredientDTO read(@PathVariable long id) {
        return this.ingredientService.get(id);
    }

    /**
     * The method matching route <code>/ingredients</code> on <strong>POST</strong>
     * Http method. This method will create a new {@link eu.yoannfleury.entity.Ingredient}
     * with the data given as a json body.
     * @param ingredient The body matching an {@link IngredientDTO} architecture.
     * @return The newly created {@link IngredientDTO}.
     */
    @RequestMapping(method = RequestMethod.POST)
    public IngredientDTO create(@Validated @RequestBody IngredientDTO ingredient) {
        this.ingredientService.exists(ingredient);

        return this.ingredientService.create(ingredient);
    }

    /**
     * The method matching route <code>/ingredients/{id}</code> on <strong>PUT</strong>
     * Http method. This method will update the {@link eu.yoannfleury.entity.Ingredient}
     * matching the id path variable.
     * @param id The index of the {@link eu.yoannfleury.entity.Ingredient} you want to update.
     * @param ingredient The new data to insert.
     * @return The {@link IngredientDTO} just being update.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public IngredientDTO update(@PathVariable long id, @Validated @RequestBody IngredientDTO ingredient) {
        return this.ingredientService.update(id, ingredient);
    }

    /**
     * The method matching route <code>/ingredients/{id}</code> on <strong>DELETE</strong>
     * Http method. This method will delete the {@link eu.yoannfleury.entity.Ingredient}
     * that match the path variable.
     * @param id The index of the {@link eu.yoannfleury.entity.Ingredient} to delete.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.ingredientService.delete(id);
    }
}
