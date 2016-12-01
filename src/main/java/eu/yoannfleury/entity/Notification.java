package eu.yoannfleury.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
