package eu.yoannfleury.entity;

/**
 * Roles available for a {@link User}
 */
public enum Role {
    USER(1f),
    PHARMACIST(3f),
    ASSOCIATION(2.5f),
    DOCTOR(5f),
    LABORATORY(4f);

    /**
     * The weight of the role. This attribute is used to
     * set a weight to a notification. The heaviest a
     * notification is, the more important it is.
     */
    private float weight;

    /**
     * Role constructor
     * @param weight The weight of the role.
     */
    Role(float weight) {
        this.weight = weight;
    }

    /**
     * Weight getter.
     * @return The weight of the role.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Weight setter.
     * @param weight The weight of the role.
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }
}
