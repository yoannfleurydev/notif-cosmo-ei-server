package eu.yoannfleury.entity;

import javax.persistence.*;
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
     *
     */
    // https://geo.api.gouv.fr/regions
    private long code;

    @ManyToMany
    private List<Effect> effects;

    @ManyToMany
    private List<Product> products;
}
