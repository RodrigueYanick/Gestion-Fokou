package fokou.dto;

import lombok.*;


import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ApprovisionnementRequestDTO {
    @NotNull(message = "L'ID du fournisseur est obligatoire")
    private Long fournisseurId;

    @NotNull(message = "L'ID du magasin est obligatoire")
    private Long magasinId;

    @NotEmpty(message = "L'approvisionnement doit contenir au moins une ligne")
    private List<LigneApprovisionnementRequest> lignes;
}