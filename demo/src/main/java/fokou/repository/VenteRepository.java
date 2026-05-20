package fokou.repository;

import com.example.gestion_stock.model.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VenteRepository extends JpaRepository<Vente, Long> {
    List<Vente> findByMagasinIdOrderByDateVenteDesc(Long magasinId);
    List<Vente> findByClientIdOrderByDateVenteDesc(Long clientId);
    List<Vente> findByVendeurIdOrderByDateVenteDesc(Long vendeurId);
}