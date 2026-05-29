package fokou.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApprovisionnementResponseDTO {
    private Long id;
    private LocalDateTime dateApprovisionnement;
    private double montantTotal;
    private String statut;

    private String fournisseurNom;
    private String magasinNom;

    private List<LigneApprovisionnementResponse> lignes;
}