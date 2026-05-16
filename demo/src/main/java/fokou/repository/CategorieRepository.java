package fokou.repository;

import com.example.gestion_stock.model.entity.Categorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    boolean existsByLibelle(String libelle);

    @Query(value = "SELECT * FROM categorie WHERE actif = false", nativeQuery = true)
    List<Categorie> findAllInactifs();

    @Modifying
    @Query(value = "UPDATE categorie SET actif = true WHERE id = :id", nativeQuery = true)
    void restaurer(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM categorie WHERE id = :id AND actif = false", nativeQuery = true)
    int countByIdInactif(@Param("id") Long id);
}
