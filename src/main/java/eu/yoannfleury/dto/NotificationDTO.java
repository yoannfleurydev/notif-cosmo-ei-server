package eu.yoannfleury.dto;

import eu.yoannfleury.entity.Notification;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public class NotificationDTO {
    /**
     * The index of the notification.
     */
    private long id;

    /**
     * The index of the user that created the notification.
     */
    @NotNull
    private long user;

    /**
     * The region code of the notification.
     */
    @NotNull(message = "error.code.notnull")
    @Min(value = 1, message = "error.code.min")
    private String code;

    /**
     * The date of the notification.
     */
    private Date date;

    /**
     * The list of effect.
     */
    @NotNull
    private List<Long> effects;

    /**
     * The list of product.
     */
    @NotNull
    private List<Long> products;

    /**
     * Default constructor.
     */
    public NotificationDTO() {}

    public NotificationDTO(long id, long user, String code, Date date, List<Long> effects, List<Long> products) {
        this.id = id;
        this.user = user;
        this.code = code;
        this.date = date;
        this.effects = effects;
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
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

    public List<Long> getEffects() {
        return effects;
    }

    public void setEffects(List<Long> effects) {
        this.effects = effects;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(List<Long> products) {
        this.products = products;
    }
}
