package fokou.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fokou.entity.Magasin;

public interface MagasinRepository extends JpaRepository<Magasin, Long> {
    @Query(value = "SELECT * FROM magasin WHERE actif = false", nativeQuery = true)
    List<Magasin> findAllInactifs();

    @Modifying
    @Query(value = "UPDATE magasin SET actif = true WHERE id = :id", nativeQuery = true)
    void restaurer(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM magasin WHERE id = :id AND actif = false", nativeQuery = true)
    int countByIdInactif(@Param("id") Long id);
}
