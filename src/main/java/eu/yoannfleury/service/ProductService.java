package eu.yoannfleury.service;

import eu.yoannfleury.dto.ProductDTO;
import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.exception.ProductAlreadyExistsException;
import eu.yoannfleury.exception.ProductNotFoundException;
import eu.yoannfleury.mapper.ProductMapper;
import eu.yoannfleury.repository.IngredientRepository;
import eu.yoannfleury.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    /**
     * The {@link Product} repository.
     */
    private final ProductRepository productRepository;

    /**
     * The {@link Ingredient} repository.
     */
    private final IngredientRepository ingredientRepository;

    /**
     * The {@link Product} mapper.
     */
    private final ProductMapper productMapper;

    /**
     * Constructor for {@link IngredientService}.
     *
     * @param productRepository The {@link Product} entity repository.
     */
    @Autowired
    public ProductService(ProductRepository productRepository,
                          IngredientRepository ingredientRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.productMapper = productMapper;
    }

    /**
     * Get one product based on the index parameter.
     *
     * @param id The index of the {@link Product} to fetch.
     * @return The product that matches with the index parameter.
     */
    public ProductDTO get(long id) {
        Product p = this.productRepository.findOne(id);
        if (p == null) {
            throw new ProductNotFoundException(Long.toString(id));
        }

        return this.productMapper.entityToDTO(p);
    }

    /**
     * @return The list of all the ingredients
     */
    public List<ProductDTO> getAll() {
        return this.productMapper.entityListToDTOList(this.productRepository.findAll());
    }

    /**
     * @param product The product to create.
     * @return The product newly created
     */
    public ProductDTO create(ProductDTO product) {
        this.productRepository.save(this.productMapper.DTOToEntity(product));

        Product p = this.productRepository.findOneByName(product.getName())
                .orElseThrow(() -> new ProductNotFoundException(
                        product.getName())
                );

        return this.productMapper.entityToDTO(p);
    }

    /**
     * @param id      The index of the product you want to create.
     * @param product The model with the new data.
     * @return The product that matches with the parameter index, with the new values.
     */
    public ProductDTO update(long id, ProductDTO product) {
        Product entity = this.productRepository.findOne(id);

        if (entity == null) {
            throw new ProductNotFoundException(id);
        }

        entity.setName(product.getName());

        if (product.getIngredients() != null) {
            for (Long ingredientId :
                    product.getIngredients()) {
                entity.addIngredient(
                        this.ingredientRepository.findOne(ingredientId)
                );
            }
        }

        return this.productMapper.entityToDTO(
                this.productRepository.saveAndFlush(entity)
        );
    }

    public void delete(long id) {
        if (this.productRepository.findOne(id) == null) {
            throw new ProductNotFoundException(id);
        }
        this.productRepository.delete(id);
    }

    /**
     * This will check if {@link Product} is unique.
     *
     * @param product The user to create.
     */
    public void exists(ProductDTO product) {
        if (this.productRepository.findOneByName(product.getName()).isPresent()) {
            throw new ProductAlreadyExistsException(product.getName());
        }
    }
}
