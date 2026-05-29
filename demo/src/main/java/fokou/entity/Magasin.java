package fokou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@SQLDelete(sql = "UPDATE magasin SET actif = false WHERE id = ?")
@Where(clause = "actif = true")
public class Magasin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    private String adresse;
    private String ville;
    private String telephone;
    private String email;

    @Column(nullable = false)
    private boolean actif = true;

    @OneToMany(mappedBy = "magasin")
    @JsonIgnore @ToString.Exclude
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    @OneToMany(mappedBy = "magasin")
    @JsonIgnore @ToString.Exclude
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "magasin")
    @JsonIgnore @ToString.Exclude
    private List<Vente> ventes = new ArrayList<>();
}