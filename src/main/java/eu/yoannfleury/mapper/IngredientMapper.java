package eu.yoannfleury.mapper;

import eu.yoannfleury.dto.IngredientDTO;
import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class IngredientMapper {
    private ProductRepository productRepository;

    @Autowired
    public IngredientMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public IngredientDTO entityToDTO(Ingredient entity) {
        List<Long> products = new LinkedList<>();

        for (Product product :
                entity.getProducts()) {
            products.add(product.getId());
        }

        return new IngredientDTO(
                entity.getId(),
                entity.getName(),
                products
        );
    }

    public Ingredient DTOToEntity(IngredientDTO dto) {
        return new Ingredient(dto.getName());
    }

    public List<IngredientDTO> entityListToDTOList(List<Ingredient> entities) {
        List<IngredientDTO> list = new LinkedList<>();

        for (Ingredient entity :
                entities) {
            list.add(entityToDTO(entity));
        }

        return list;
    }
}
