package eu.yoannfleury.mapper;

import eu.yoannfleury.dto.ProductDTO;
import eu.yoannfleury.entity.Ingredient;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.exception.IngredientNotFoundException;
import eu.yoannfleury.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductMapper {
    private IngredientRepository ingredientRepository;

    public ProductMapper(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public ProductDTO entityToDTO(Product entity) {
        List<Long> ingredients = new LinkedList<>();

        for (Ingredient ingredient :
                entity.getIngredients()) {
            ingredients.add(ingredient.getId());
        }

        return new ProductDTO(
                entity.getId(),
                entity.getName(),
                ingredients
        );
    }

    public Product DTOToEntity(ProductDTO dto) {
        List<Ingredient> ingredients = new LinkedList<>();

        for (Long id :
                dto.getIngredients()) {
            Ingredient i = this.ingredientRepository.findOne(id);
            if (i == null) {
                throw new IngredientNotFoundException(id);
            }
            ingredients.add(i);
        }
        return new Product(dto.getName(), ingredients);
    }

    public List<ProductDTO> entityListToDTOList(List<Product> entities) {
        List<ProductDTO> list = new LinkedList<>();

        for (Product entity :
                entities) {
            list.add(entityToDTO(entity));
        }

        return list;
    }
}
