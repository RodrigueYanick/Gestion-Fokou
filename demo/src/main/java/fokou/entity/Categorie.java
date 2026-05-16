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
@SQLDelete(sql = "UPDATE categorie SET actif = false WHERE id = ?")
@Where(clause = "actif = true")
public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String libelle;

    private String description;


    @Column(nullable = false)
    private boolean actif = true;

    @OneToMany(mappedBy = "categorie")
    @JsonIgnore @ToString.Exclude
    private List<Produit> produits = new ArrayList<>();
}
