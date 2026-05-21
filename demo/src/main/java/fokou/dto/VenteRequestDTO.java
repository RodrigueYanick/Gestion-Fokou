package fokou.dto;

import lombok.*;


import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
public class VenteRequestDTO {
    @NotNull(message = "L'ID du client est obligatoire")
    private Long clientId;

    @NotNull(message = "L'ID du magasin est obligatoire")
    private Long magasinId;

    @NotEmpty(message = "Le mode de paiement est obligatoire")
    private String modePaiement;

    @NotEmpty(message = "La vente doit contenir au moins une ligne")
    private List<LigneVenteRequest> lignes;
}
