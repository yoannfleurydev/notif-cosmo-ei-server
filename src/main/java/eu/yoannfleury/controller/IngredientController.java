package eu.yoannfleury.controller;

import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.exception.IngredientAlreadyExistsException;
import eu.yoannfleury.exception.IngredientNotFoundException;
import eu.yoannfleury.exception.UnprocessableEntityException;
import eu.yoannfleury.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

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
    public Ingredient add(@RequestBody Ingredient ingredient) {
        validate(ingredient);
        exists(ingredient);

        this.ingredientRepository.save(ingredient);

        return this.ingredientRepository.findOneByName(ingredient.getName())
                .orElseThrow(() -> new IngredientNotFoundException(
                        ingredient.getName())
                );
    }

    /**
     * This will validate the {@link Ingredient}.
     * @param ingredient The ingredient to create.
     */
    private void validate(Ingredient ingredient) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Ingredient>> constraintViolations = validator.validate(ingredient);

        if (constraintViolations.size() > 0) {
            String message = "";
            for (ConstraintViolation<Ingredient> constraintViolation:
                    constraintViolations){
                message += ", " + constraintViolation.getRootBeanClass().getSimpleName() + "."
                        + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage();
            }

            throw new UnprocessableEntityException(message);
        }
    }

    /**
     * This will check if {@link Ingredient} is unique.
     * @param user The user to create.
     */
    private void exists(Ingredient user) {
        if (this.ingredientRepository.findOneByName(user.getName()).isPresent()) {
            throw new IngredientAlreadyExistsException(user.getName());
        }
    }
}
