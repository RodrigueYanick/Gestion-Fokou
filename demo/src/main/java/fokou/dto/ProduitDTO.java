package fokou.dto;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ProduitDTO {
    private Long id;
    private String reference;
    private String nom;
    private String description;
    private double prixUnitaire;
    private String uniteMesure;
    private int seuilAlerte;
    private String statut;
    private Long categorieId;
    private String categorieLibelle;
}