package fokou.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@SQLDelete(sql = "UPDATE utilisateur SET actif = false WHERE id = ?")
@Where(clause = "actif = true")
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    private String prenom;
    @Column(nullable = false)
    private String telephone;
    private String email;
    private String adresse;
    private LocalDate dateCreation;

    @Column(nullable = false)
    private boolean actif = true;

    @OneToMany(mappedBy = "client")
    @JsonIgnore @ToString.Exclude
    private List<Vente> ventes = new ArrayList<>();
}