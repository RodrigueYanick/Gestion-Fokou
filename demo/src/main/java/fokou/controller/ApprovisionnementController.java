package fokou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_stock.dto.ApprovisionnementRequestDTO;
import com.example.gestion_stock.dto.ApprovisionnementResponseDTO;
import com.example.gestion_stock.service.ApprovisionnementService;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/approvisionnements")
@RequiredArgsConstructor
public class ApprovisionnementController {

    private final ApprovisionnementService approvisionnementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('STOCK')")
    public ResponseEntity<ApprovisionnementResponseDTO> enregistrer(@Valid @RequestBody ApprovisionnementRequestDTO dto, Principal principal) {
        return ResponseEntity.ok(approvisionnementService.enregistrerApprovisionnement(dto, principal.getName()));
    }

    @GetMapping("/magasin/{magasinId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<List<ApprovisionnementResponseDTO>> listeParMagasin(@PathVariable Long magasinId) {
        // Ajouter la méthode dans le service si manquante
        return ResponseEntity.ok(approvisionnementService.listerParMagasin(magasinId));
    }
}