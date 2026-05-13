package fokou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Approvisionnement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateApprovisionnement;
    private double montantTotal;
    private String statut; // EN_ATTENTE, LIVRE, ANNULE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id")
    @JsonIgnore @ToString.Exclude
    private Fournisseur fournisseur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    @JsonIgnore @ToString.Exclude
    private Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magasin_id")
    @JsonIgnore @ToString.Exclude
    private Magasin magasin;

    @OneToMany(mappedBy = "approvisionnement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LigneApprovisionnement> lignesApprovisionnement = new ArrayList<>();

    public void addLigneApprovisionnement(LigneApprovisionnement ligne) {
        lignesApprovisionnement.add(ligne);
        ligne.setApprovisionnement(this);
    }
}