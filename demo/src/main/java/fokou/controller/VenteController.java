package fokou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_stock.dto.VenteRequestDTO;
import com.example.gestion_stock.dto.VenteResponseDTO;
import com.example.gestion_stock.service.VenteService;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@RequiredArgsConstructor
public class VenteController {

    private final VenteService venteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('VENDEUR')")
    public ResponseEntity<VenteResponseDTO> creerVente(@Valid @RequestBody VenteRequestDTO dto, Principal principal) {
        return ResponseEntity.ok(venteService.creerVente(dto, principal.getName()));
    }

    @GetMapping("/magasin/{magasinId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('VENDEUR')")
    public ResponseEntity<List<VenteResponseDTO>> listeParMagasin(@PathVariable Long magasinId) {
        return ResponseEntity.ok(venteService.listerParMagasin(magasinId));
    }

    // On peut ajouter une méthode pour les ventes par client
}