package fokou.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fokou.entity.Stock;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProduitIdAndMagasinId(Long produitId, Long magasinId);
    List<Stock> findByMagasinId(Long magasinId);
    List<Stock> findByMagasinIdAndQuantiteDisponibleLessThan(Long magasinId, int quantite);
}