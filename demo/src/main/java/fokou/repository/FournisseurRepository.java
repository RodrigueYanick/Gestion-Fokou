package fokou.repository;

import com.example.gestion_stock.model.entity.Fournisseur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    @Query(value = "SELECT * FROM fournisseur WHERE actif = false", nativeQuery = true)
    List<Fournisseur> findAllInactifs();

    @Modifying
    @Query(value = "UPDATE fournisseur SET actif = true WHERE id = :id", nativeQuery = true)
    void restaurer(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM fournisseur WHERE id = :id AND actif = false", nativeQuery = true)
    int countByIdInactif(@Param("id") Long id);
    
}
