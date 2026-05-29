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
@SQLDelete(sql = "UPDATE produit SET actif = false WHERE id = ?")
@Where(clause = "actif = true")
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String reference;
    @Column(nullable = false)
    private String nom;
    private String description;
    private double prixUnitaire;
    private String uniteMesure;
    private int seuilAlerte;
    private String statut; // ACTIF, INACTIF

    @Column(nullable = false)
    private boolean actif = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore @ToString.Exclude
    private List<LigneVente> lignesVente = new ArrayList<>();

    @OneToMany(mappedBy = "produit")
    @JsonIgnore @ToString.Exclude
    private List<LigneApprovisionnement> lignesApprovisionnement = new ArrayList<>();

    @OneToMany(mappedBy = "produit")
    @JsonIgnore @ToString.Exclude
    private List<Stock> stocks = new ArrayList<>();
}