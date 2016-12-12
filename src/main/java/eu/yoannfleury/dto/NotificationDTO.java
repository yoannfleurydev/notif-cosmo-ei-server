package eu.yoannfleury.dto;

import eu.yoannfleury.entity.Notification;

import javax.validation.constraints.NotNull;
import java.util.List;

public class NotificationDTO {
    /**
     * The index of the notification.
     */
    private long id;

    /**
     * The region code of the notification
     */
    @NotNull
    private long code;

    @NotNull
    private List<Long> effects;

    @NotNull
    private List<Long> products;

    public NotificationDTO() {}

    public NotificationDTO(long id, long code, List<Long> effects, List<Long> products) {
        this.id = id;
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
