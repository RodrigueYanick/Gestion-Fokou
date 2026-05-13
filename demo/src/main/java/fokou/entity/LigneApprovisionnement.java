package fokou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class LigneApprovisionnement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantite;
    private double prixAchat;
    private double sousTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approvisionnement_id")
    @JsonIgnore @ToString.Exclude
    private Approvisionnement approvisionnement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_id")
    private Produit produit;
}