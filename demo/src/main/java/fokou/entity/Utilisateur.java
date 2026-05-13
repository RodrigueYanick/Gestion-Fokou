package fokou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@SQLDelete(sql = "UPDATE utilisateur SET actif = false WHERE id = ?")
@Where(clause = "actif = true")
// ... (exemple pour Categorie; à adapter pour chaque table)
public class Utilisateur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String prenom;
    @Column(unique = true, nullable = false)
    private String email;
    private String telephone;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String motDePasse;
    private String statut; // ACTIF, INACTIF

    @Column(nullable = false)
    private boolean actif = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magasin_id")
    @JsonIgnore @ToString.Exclude
    private Magasin magasin;

    @OneToMany(mappedBy = "vendeur")
    @JsonIgnore @ToString.Exclude
    private List<Vente> ventes = new ArrayList<>();

    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore @ToString.Exclude
    private List<Historique> historiques = new ArrayList<>();
}
