package fokou.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gestion_stock.dto.StockDTO;
import com.example.gestion_stock.model.entity.Stock;
import com.example.gestion_stock.repository.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;

    public List<StockDTO> stockParMagasin(Long magasinId) {
        return stockRepository.findByMagasinId(magasinId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<StockDTO> alertesStock(Long magasinId) {
        // on récupère tous les stocks du magasin, puis on filtre selon le seuil du produit
        List<Stock> stocks = stockRepository.findByMagasinId(magasinId);
        return stocks.stream()
                .filter(s -> s.getQuantiteDisponible() < s.getProduit().getSeuilAlerte())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public StockDTO consulter(Long id) {
        return mapToDTO(stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock introuvable")));
    }

    private StockDTO mapToDTO(Stock s) {
        StockDTO dto = new StockDTO();
        dto.setId(s.getId());
        dto.setQuantiteDisponible(s.getQuantiteDisponible());
        dto.setProduitId(s.getProduit().getId());
        dto.setProduitNom(s.getProduit().getNom());
        dto.setMagasinId(s.getMagasin().getId());
        dto.setMagasinNom(s.getMagasin().getNom());
        return dto;
    }
}