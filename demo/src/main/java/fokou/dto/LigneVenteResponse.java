package fokou.dto;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LigneVenteResponse {
    private String produitNom;
    private int quantite;
    private double prixUnitaire;
    private double sousTotal;
}
