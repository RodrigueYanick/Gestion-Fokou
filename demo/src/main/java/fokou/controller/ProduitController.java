package fokou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_stock.dto.ProduitDTO;
import com.example.gestion_stock.service.ProduitService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('VENDEUR') or hasRole('STOCK')")
    public ResponseEntity<List<ProduitDTO>> lister() {
        return ResponseEntity.ok(produitService.lister());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('VENDEUR') or hasRole('STOCK')")
    public ResponseEntity<ProduitDTO> consulter(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.consulter(id));
    }

    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        produitService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recherche")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('VENDEUR') or hasRole('STOCK')")
    public ResponseEntity<List<ProduitDTO>> rechercher(@RequestParam String nom) {
        return ResponseEntity.ok(produitService.rechercherParNom(nom));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<ProduitDTO> ajouter(@Valid @RequestBody ProduitDTO dto) {
        return ResponseEntity.ok(produitService.ajouter(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<ProduitDTO> modifier(@PathVariable Long id, @Valid @RequestBody ProduitDTO dto) {
        return ResponseEntity.ok(produitService.modifier(id, dto));
    }

    @PutMapping("/restaurer/{id}")
    @PreAuthorize("hasRole('ADMIN')")   
    public ResponseEntity<Void> restaurer(@PathVariable Long id) {
        produitService.restaurer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/inactifs")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<List<ProduitDTO>> listerInactifs() {
        return ResponseEntity.ok(produitService.listerInactifs());
    }
}