package eu.yoannfleury.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Notification entity
 */
@Entity
public class Notification {

    /**
     * The index of the notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The code of the regions that matches with the french government API.
     * https://geo.api.gouv.fr/regions
     */
    @NotNull
    private long code;

    @ManyToMany
    private List<Effect> effects;

    @ManyToMany
    private List<Product> products;

    public Notification() {}

    public Notification(long code, List<Effect> effects, List<Product> products) {
        this.code = code;
        this.effects = effects;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
