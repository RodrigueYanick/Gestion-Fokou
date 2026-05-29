package fokou.dto;

import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
public class UtilisateurDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    private String telephone;

    @NotBlank(message = "Le login est obligatoire")
    private String login;

    // mot de passe uniquement à la création, pas de getter exposé aux retours
    private String motDePasse;

    @NotNull(message = "Le rôle est obligatoire")
    private Long roleId;              // on passe l'ID du rôle

    private Long magasinId;           // optionnel selon le rôle
}