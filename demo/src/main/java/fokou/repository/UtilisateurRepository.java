package fokou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fokou.entity.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    
    @Query(value = "SELECT * FROM utilisateur WHERE actif = false", nativeQuery = true)
    List<Utilisateur> findAllInactifs();

    @Modifying
    @Query(value = "UPDATE utilisateur SET actif = true WHERE id = :id", nativeQuery = true)
    void restaurer(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM utilisateur WHERE id = :id AND actif = false", nativeQuery = true)
    int countByIdInactif(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM utilisateur WHERE login = :login", nativeQuery = true)
    int countByLoginIgnoreFilter(@Param("login") String login);
}
