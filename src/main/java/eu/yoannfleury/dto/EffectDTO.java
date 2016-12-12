package eu.yoannfleury.dto;

import eu.yoannfleury.entity.Level;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EffectDTO {
    private long id;

    @NotNull
    @Size(min = 1, max = 100, message = "error.description.size")
    private String description;

    @NotNull
    private Level level;

    public EffectDTO() {}

    public EffectDTO(long id, String description, Level level) {
        this.id = id;
        this.description = description;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
