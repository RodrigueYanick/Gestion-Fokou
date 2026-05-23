package fokou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_stock.model.entity.MouvementStock;
import com.example.gestion_stock.repository.MouvementStockRepository;

import java.util.List;

@RestController
@RequestMapping("/api/mouvements-stock")

@RequiredArgsConstructor
public class MouvementStockController {
    

    private final MouvementStockRepository mouvementStockRepository;

    @GetMapping("/produit/{produitId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<List<MouvementStock>> parProduit(@PathVariable Long produitId) {
        return ResponseEntity.ok(mouvementStockRepository.findByProduitIdOrderByDateMouvementDesc(produitId));
    }

    @GetMapping("/magasin/{magasinId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<List<MouvementStock>> parMagasin(@PathVariable Long magasinId) {
        return ResponseEntity.ok(mouvementStockRepository.findByMagasinIdOrderByDateMouvementDesc(magasinId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<MouvementStock> consulter(@PathVariable Long id) {
        return ResponseEntity.ok(mouvementStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mouvement introuvable")));
    }
    
}