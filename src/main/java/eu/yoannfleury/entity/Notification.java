package eu.yoannfleury.entity;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
     * The user that created the notification.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * The code of the regions that matches with the french government API.
     * https://geo.api.gouv.fr/regions
     */
    @NotNull
    @NumberFormat
    private String code;

    /**
     * The date of the notification.
     */
    @NotNull
    private Date date;

    /**
     * The list of effects.
     */
    @ManyToMany
    private List<Effect> effects;

    /**
     * The list of products.
     */
    @ManyToMany
    private List<Product> products;

    /**
     * The default notification's constructor
     */
    public Notification() {}

    /**
     * The full parameters constructor.
     * @param code The region's code.
     * @param date The date of the notification.

     * @param effects The list of the effects.
     * @param products The list of products.
     */
    public Notification(String code, User user, Date date, List<Effect> effects, List<Product> products) {
        this.code = code;
        this.user = user;
        this.date= date;
        this.effects = effects;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
