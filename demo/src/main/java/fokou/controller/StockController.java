package fokou.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.gestion_stock.dto.StockDTO;
import com.example.gestion_stock.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/magasin/{magasinId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<List<StockDTO>> stockParMagasin(@PathVariable Long magasinId) {
        return ResponseEntity.ok(stockService.stockParMagasin(magasinId));
    }

    @GetMapping("/alertes/{magasinId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<List<StockDTO>> alertes(@PathVariable Long magasinId) {
        return ResponseEntity.ok(stockService.alertesStock(magasinId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GESTIONNAIRE') or hasRole('STOCK')")
    public ResponseEntity<StockDTO> consulter(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.consulter(id));
    }
}