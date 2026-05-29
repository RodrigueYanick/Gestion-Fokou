package fokou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_stock.dto.CategorieDto;
import com.example.gestion_stock.service.CategorieService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<List<CategorieDto>> lister() {
        return ResponseEntity.ok(categorieService.lister());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<CategorieDto> creer(@Valid @RequestBody CategorieDto dto) {
        return ResponseEntity.ok(categorieService.creer(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<CategorieDto> modifier(@PathVariable Long id, @Valid @RequestBody CategorieDto dto) {
        return ResponseEntity.ok(categorieService.modifier(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        categorieService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<CategorieDto> consulter(@PathVariable Long id) {
        return ResponseEntity.ok(categorieService.consulter(id));
    }

    @GetMapping("/inactifs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategorieDto>> listerInactifs() {
        return ResponseEntity.ok(categorieService.listerInactifs());
    }

    @PutMapping("/restaurer/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> restaurer(@PathVariable Long id) {
        categorieService.restaurer(id);
        return ResponseEntity.ok().build();
    }
}