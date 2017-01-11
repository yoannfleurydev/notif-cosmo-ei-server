package eu.yoannfleury.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The {@link RegionDTO} match the objects given by the french government api at
 * https://geo.api.gouv.fr/regions endpoint. This has nothing to do with the
 * objects given by this service.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegionDTO {
    /**
     * The name of the region. The name of the attribute is given in french to
     * match with the api.
     */
    private String nom;

    /**
     * The code of the region.
     */
    private String code;

    /**
     * Default constructor.
     */
    public RegionDTO() {}

    /**
     * Constructor with parameters.
     * @param nom The name of the region.
     * @param code The code of the region.
     */
    public RegionDTO(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
