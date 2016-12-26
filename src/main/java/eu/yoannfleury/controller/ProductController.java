package eu.yoannfleury.controller;

import eu.yoannfleury.dto.ProductDTO;
import eu.yoannfleury.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping
    public List<ProductDTO> products(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "property", defaultValue = "id") String property
    ) {
        return this.productService.getByPagination(page, limit, direction, property);
    }

    @RequestMapping("/{id}")
    public ProductDTO read(@PathVariable long id) {
        return this.productService.get(id);
    }

    @RequestMapping("/search")
    public List<ProductDTO> search(@RequestParam(value = "value", defaultValue = "") String value) {
        return this.productService.search(value);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProductDTO create(@Validated @RequestBody ProductDTO product) {
        this.productService.exists(product);

        return this.productService.create(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ProductDTO update(@PathVariable long id, @Validated @RequestBody ProductDTO product) {
        return this.productService.update(id, product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.productService.delete(id);
    }
}
