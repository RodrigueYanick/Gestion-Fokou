package fokou.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VenteResponseDTO {
    private Long id;
    private LocalDateTime dateVente;
    private double montantTotal;
    private String modePaiement;
    private String statutVente;

    private String vendeurNom;
    private String clientNom;
    private String magasinNom;

    private List<LigneVenteResponse> lignes;
}
