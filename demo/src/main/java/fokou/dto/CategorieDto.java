package fokou.dto;

import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor

public class CategorieDto {
    private Long id;
    private String libelle;
    private String description;
}
