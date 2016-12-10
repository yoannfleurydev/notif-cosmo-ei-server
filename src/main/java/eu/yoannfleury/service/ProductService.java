package eu.yoannfleury.service;

import eu.yoannfleury.exception.ProductAlreadyExistsException;
import eu.yoannfleury.exception.ProductNotFoundException;
import eu.yoannfleury.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import eu.yoannfleury.entity.Product;

import java.util.List;

@Service
public class ProductService {
    /**
     * The {@link Product} repository.
     */
    private final ProductRepository productRepository;

    /**
     * Constructor for {@link IngredientService}.
     * @param productRepository The {@link Product} entity repository.
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get one product based on the index parameter.
     * @param id The index of the {@link Product} to fetch.
     * @return The product that matches with the index parameter.
     */
    public Product get(long id) {
        if (this.productRepository.findOne(id) == null) {
            throw new ProductNotFoundException(Long.toString(id));
        }

        return this.productRepository.findOne(id);
    }

    /**
     *
     * @return The list of all the ingredients
     */
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    /**
     *
     * @param product The product to create.
     * @return The product newly created
     */
    public Product create(Product product) {
        this.productRepository.save(product);

        return this.productRepository.findOneByName(product.getName())
                .orElseThrow(() -> new ProductNotFoundException(
                        product.getName())
                );
    }

    /**
     *
     * @param id The index of the product you want to create.
     * @param product The model with the new data.
     * @return The product that matches with the parameter index, with the new values.
     */
    public Product update(long id, Product product) {
        Product entity = this.productRepository.findOne(id);
        if (entity == null) {
            throw new ProductNotFoundException(id);
        }

        entity.setName(product.getName());
        entity.setIngredients(product.getIngredients());

        return this.productRepository.saveAndFlush(entity);
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
    public void exists(Product product) {
        if (this.productRepository.findOneByName(product.getName()).isPresent()) {
            throw new ProductAlreadyExistsException(product.getName());
        }
    }
}
