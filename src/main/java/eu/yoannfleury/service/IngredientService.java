package eu.yoannfleury.service;

import eu.yoannfleury.dto.IngredientDTO;
import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.exception.IngredientAlreadyExistsException;
import eu.yoannfleury.exception.IngredientNotFoundException;
import eu.yoannfleury.mapper.IngredientMapper;
import eu.yoannfleury.repository.IngredientRepository;
import eu.yoannfleury.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class IngredientService {
    /**
     * The {@link Ingredient} repository.
     */
    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    private final ProductRepository productRepository;

    /**
     * Constructor for {@link IngredientService}.
     *
     * @param ingredientRepository The {@link Ingredient} entity repository.
     */
    @Autowired
    public IngredientService(IngredientRepository ingredientRepository,
                             IngredientMapper ingredientMapper,
                             ProductRepository productRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
        this.productRepository = productRepository;
    }

    /**
     * Get one ingredient based on the index parameter.
     *
     * @param id The index of the {@link Ingredient} to fetch.
     * @return The ingredient that matches with the index parameter.
     */
    public IngredientDTO get(long id) {
        Ingredient ingredient = this.ingredientRepository.findOne(id);
        if (ingredient == null) {
            throw new IngredientNotFoundException(Long.toString(id));
        }

        return this.ingredientMapper.entityToDTO(ingredient);
    }

    /**
     * Get all the persisted ingredients.
     *
     * @return The list of all the ingredients.
     */
    public List<IngredientDTO> getAll() {
        return this.ingredientMapper.entityListToDTOList(
                this.ingredientRepository.findAll()
        );
    }

    public List<IngredientDTO> getByPagination(int page, int limit,
                                               Sort.Direction direction,
                                               String property) {
        Pageable pageable = new PageRequest(page, limit, direction, property);

        Page<Ingredient> ingredientPage = this.ingredientRepository.findAll(pageable);

        return this.ingredientMapper.entityListToDTOList(ingredientPage.getContent());
    }

    /**
     * Search for an ingredient by its name.
     *
     * @param pattern The pattern of the ingredient's name to find.
     * @return A list of {@link IngredientDTO}.
     */
    public List<IngredientDTO> search(String pattern) {
        return this.ingredientMapper.entityListToDTOList(
                this.ingredientRepository.findByPattern("%" + pattern + "%")
        );
    }

    /**
     * Create a new {@link Ingredient}.
     *
     * @param ingredient The ingredient to create.
     * @return The ingredient newly created.
     */
    public IngredientDTO create(IngredientDTO ingredient) {
        this.ingredientRepository.save(
                this.ingredientMapper.DTOToEntity(ingredient)
        );

        Ingredient i = this.ingredientRepository.findOneByName(ingredient.getName())
                .orElseThrow(() -> new IngredientNotFoundException(
                        ingredient.getName())
                );

        return this.ingredientMapper.entityToDTO(i);
    }

    /**
     * Update an existing {@link Ingredient}.
     *
     * @param id         The index of the ingredient you want to create.
     * @param ingredient The model with the new data.
     * @return The ingredient that matches with the parameter index, with the new values.
     */
    public IngredientDTO update(long id, IngredientDTO ingredient) {
        Ingredient entity = this.ingredientRepository.findOne(id);

        if (entity == null) {
            throw new IngredientNotFoundException(id);
        }

        entity.setName(ingredient.getName());

        List<Product> products = new LinkedList<>();

        for (Long productId : ingredient.getProducts()) {
            products.add(this.productRepository.findOne(productId));
        }

        entity.setProducts(products);

        return this.ingredientMapper.entityToDTO(
                this.ingredientRepository.saveAndFlush(entity)
        );
    }

    /**
     * Delete an {@link Ingredient}.
     *
     * @param id The index of the {@link Ingredient} to delete.
     */
    public void delete(long id) {
        if (this.ingredientRepository.findOne(id) == null) {
            throw new IngredientNotFoundException(id);
        }
        this.ingredientRepository.delete(id);
    }

    /**
     * This will check if the {@link Ingredient} is unique.
     *
     * @param ingredient The user to create.
     */
    public void exists(IngredientDTO ingredient) {
        if (this.ingredientRepository.findOneByName(ingredient.getName()).isPresent()) {
            throw new IngredientAlreadyExistsException(ingredient.getName());
        }
    }
}
