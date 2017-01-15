package eu.yoannfleury.repository;

import eu.yoannfleury.entity.Notification;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findByProducts(List<Product> products);

    public List<Notification> findByUser(User user);
}
