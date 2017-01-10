package eu.yoannfleury.service;

import eu.yoannfleury.dto.ProductDTO;
import eu.yoannfleury.dto.composed.EffectsNotificationsByProductDTO;
import eu.yoannfleury.entity.Effect;
import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.entity.Notification;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.exception.ProductAlreadyExistsException;
import eu.yoannfleury.exception.ProductNotFoundException;
import eu.yoannfleury.mapper.EffectMapper;
import eu.yoannfleury.mapper.NotificationMapper;
import eu.yoannfleury.mapper.ProductMapper;
import eu.yoannfleury.repository.EffectRepository;
import eu.yoannfleury.repository.IngredientRepository;
import eu.yoannfleury.repository.NotificationRepository;
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
     * The {@link Notification} repository.
     */
    private final NotificationRepository notificationRepository;

    /**
     * The {@link Effect} repository.
     */
    private final EffectRepository effectRepository;

    /**
     * The {@link Product} mapper.
     */
    private final ProductMapper productMapper;

    /**
     * The {@link Effect} mapper.
     */
    private final EffectMapper effectMapper;

    /**
     * The {@link Notification}
     */
    private final NotificationMapper notificationMapper;

    /**
     * Constructor for {@link IngredientService}.
     *
     * @param productRepository The {@link Product} entity repository.
     * @param ingredientRepository The {@link Ingredient} entity repository.
     * @param productMapper The {@link Product} mapper.
     * @param effectMapper The {@link Effect} mapper.
     * @param notificationMapper The {@link Notification} mapper.
     */
    @Autowired
    public ProductService(ProductRepository productRepository,
                          IngredientRepository ingredientRepository,
                          NotificationRepository notificationRepository,
                          EffectRepository effectRepository,
                          ProductMapper productMapper,
                          EffectMapper effectMapper,
                          NotificationMapper notificationMapper) {
        this.productRepository = productRepository;
        this.ingredientRepository = ingredientRepository;
        this.notificationRepository = notificationRepository;
        this.effectRepository = effectRepository;
        this.productMapper = productMapper;
        this.effectMapper = effectMapper;
        this.notificationMapper = notificationMapper;
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
     * @return The list of all the {@link Product}
     */
    public List<ProductDTO> getAll() {
        return this.productMapper.entityListToDTOList(this.productRepository.findAll());
    }

    public List<ProductDTO> getByPagination(int page, int limit,
                                               Sort.Direction direction,
                                               String property) {
        Pageable pageable = new PageRequest(page, limit, direction, property);

        Page<Product> productPage = this.productRepository.findAll(pageable);

        return this.productMapper.entityListToDTOList(productPage.getContent());
    }

    public List<ProductDTO> search(String pattern) {
        return this.productMapper.entityListToDTOList(
                this.productRepository.findByPattern("%" + pattern + "%")
        );
    }

    /**
     * @param product The {@link Product} to create and save.
     * @return The {@link Product} newly created.
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
     * @param id      The index of the {@link Product} you want to create.
     * @param product The model with the new data.
     * @return The {@link Product} that matches with the parameter index, with the new values.
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
     * @param product The user to create.
     */
    public void exists(ProductDTO product) {
        if (this.productRepository.findOneByName(product.getName()).isPresent()) {
            throw new ProductAlreadyExistsException(product.getName());
        }
    }

    /**
     *
     * @param id The index of the {@link Product} to fetch.
     * @return A {@link EffectsNotificationsByProductDTO} with all the data
     * associated to the {@link Product}.
     */
    public EffectsNotificationsByProductDTO getEffectsNotificationsByProduct(long id) {
        Product p = this.productRepository.findOne(id);
        if (p == null) {
            throw new ProductNotFoundException(Long.toString(id));
        }

        List<Product> products = new LinkedList<>();
        products.add(p);

        List<Notification> notifications = this.notificationRepository.findByProducts(products);

        EffectsNotificationsByProductDTO dto = new EffectsNotificationsByProductDTO();
        dto.setProduct(this.productMapper.entityToDTO(p));
        dto.setNotifications(this.notificationMapper.entityListToDTOList(notifications));
        dto.setEffects(this.effectMapper.entityListToDTOList(this.effectRepository.findByNotifications(notifications)));

        return dto;
    }
}
