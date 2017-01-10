package eu.yoannfleury.dto.composed;

import eu.yoannfleury.dto.EffectDTO;
import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.dto.ProductDTO;

import java.util.LinkedList;
import java.util.List;

public class EffectsNotificationsByProductDTO {
    private ProductDTO product;
    private List<EffectDTO> effects;
    private List<NotificationDTO> notifications;

    public EffectsNotificationsByProductDTO() {
        this.effects = new LinkedList<>();
        this.notifications = new LinkedList<>();
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public List<EffectDTO> getEffects() {
        return effects;
    }

    public void setEffects(List<EffectDTO> effects) {
        this.effects = effects;
    }

    public List<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    public void addEffect(EffectDTO effect) {
        this.effects.add(effect);
    }

    public void addNotification(NotificationDTO notification) {
        this.notifications.add(notification);
    }
}
