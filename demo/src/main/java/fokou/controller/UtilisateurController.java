package fokou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import fokou.dto.UtilisateurDTO;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UtilisateurDTO>> lister() {
        return ResponseEntity
        .ok(utilisateurService.lister());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<UtilisateurDTO> creer(@Valid @RequestBody UtilisateurDTO dto) {
        
        return ResponseEntity
        .ok(utilisateurService.creer(dto));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtilisateurDTO> consulter(@PathVariable Long id) {
        return ResponseEntity
        .ok(utilisateurService.consulter(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UtilisateurDTO> modifier(@PathVariable Long id, @Valid @RequestBody UtilisateurDTO dto) {
        return ResponseEntity
        .ok(utilisateurService.modifier(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        utilisateurService.supprimer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/inactifs")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<List<UtilisateurDTO>> listerInactifs() {
        return ResponseEntity.ok(utilisateurService.listerInactifs());
    }

    @PutMapping("/restaurer/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE')")
    public ResponseEntity<Void> restaurer(@PathVariable Long id) {
        utilisateurService.restaurer(id);
        return ResponseEntity.ok().build();
    }


}