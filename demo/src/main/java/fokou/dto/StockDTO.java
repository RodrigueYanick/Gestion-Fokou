package fokou.dto;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
public class StockDTO {
    private Long id;
    private int quantiteDisponible;
    private Long produitId;
    private String produitNom;
    private Long magasinId;
    private String magasinNom;
}