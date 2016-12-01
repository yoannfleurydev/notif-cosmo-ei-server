package eu.yoannfleury.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Effect {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String description;
}
