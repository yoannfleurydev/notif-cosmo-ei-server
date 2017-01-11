package eu.yoannfleury.repository;

import eu.yoannfleury.entity.Effect;
import eu.yoannfleury.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EffectRepository extends JpaRepository<Effect, Long> {
    @Query(value = "SELECT eff FROM Effect eff WHERE eff.description LIKE :pattern")
    public List<Effect> findByPattern(@Param("pattern") String pattern);

    public List<Effect> findByNotifications(List<Notification> notifications);
}
