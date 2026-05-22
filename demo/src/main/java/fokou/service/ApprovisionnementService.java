package fokou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gestion_stock.dto.ApprovisionnementRequestDTO;
import com.example.gestion_stock.dto.ApprovisionnementResponseDTO;
import com.example.gestion_stock.dto.LigneApprovisionnementRequest;
import com.example.gestion_stock.dto.LigneApprovisionnementResponse;
import com.example.gestion_stock.model.entity.Approvisionnement;
import com.example.gestion_stock.model.entity.Fournisseur;
import com.example.gestion_stock.model.entity.LigneApprovisionnement;
import com.example.gestion_stock.model.entity.Magasin;
import com.example.gestion_stock.model.entity.MouvementStock;
import com.example.gestion_stock.model.entity.Produit;
import com.example.gestion_stock.model.entity.Stock;
import com.example.gestion_stock.model.entity.Utilisateur;
import com.example.gestion_stock.repository.ApprovisionnementRepository;
import com.example.gestion_stock.repository.FournisseurRepository;
import com.example.gestion_stock.repository.MagasinRepository;
import com.example.gestion_stock.repository.MouvementStockRepository;
import com.example.gestion_stock.repository.ProduitRepository;
import com.example.gestion_stock.repository.StockRepository;
import com.example.gestion_stock.repository.UtilisateurRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovisionnementService {

    private final ApprovisionnementRepository approvisionnementRepository;
    private final ProduitRepository produitRepository;
    private final StockRepository stockRepository;
    private final MouvementStockRepository mouvementStockRepository;
    private final FournisseurRepository fournisseurRepository;
    private final MagasinRepository magasinRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ApprovisionnementResponseDTO enregistrerApprovisionnement(ApprovisionnementRequestDTO dto, String loginUtilisateur) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(loginUtilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Fournisseur fournisseur = fournisseurRepository.findById(dto.getFournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur introuvable"));
        Magasin magasin = magasinRepository.findById(dto.getMagasinId())
                .orElseThrow(() -> new RuntimeException("Magasin introuvable"));

        Approvisionnement appro = new Approvisionnement();
        appro.setDateApprovisionnement(LocalDateTime.now());
        appro.setStatut("LIVRE");
        appro.setFournisseur(fournisseur);
        appro.setUtilisateur(utilisateur);
        appro.setMagasin(magasin);
        appro.setMontantTotal(0);

        for (LigneApprovisionnementRequest ligneReq : dto.getLignes()) {
            Produit produit = produitRepository.findById(ligneReq.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit ID " + ligneReq.getProduitId() + " introuvable"));

            // Récupérer ou créer le stock pour ce produit/magasin
            Stock stock = stockRepository.findByProduitIdAndMagasinId(produit.getId(), magasin.getId())
                    .orElseGet(() -> {
                        Stock newStock = new Stock();
                        newStock.setProduit(produit);
                        newStock.setMagasin(magasin);
                        newStock.setQuantiteDisponible(0);
                        return stockRepository.save(newStock);
                    });

            // Ligne approvisionnement
            LigneApprovisionnement ligne = new LigneApprovisionnement();
            ligne.setProduit(produit);
            ligne.setQuantite(ligneReq.getQuantite());
            ligne.setPrixAchat(ligneReq.getPrixAchat());
            double sousTotal = ligneReq.getQuantite() * ligneReq.getPrixAchat();
            ligne.setSousTotal(sousTotal);
            appro.addLigneApprovisionnement(ligne);
            appro.setMontantTotal(appro.getMontantTotal() + sousTotal);

            // Mise à jour du stock
            stock.setQuantiteDisponible(stock.getQuantiteDisponible() + ligneReq.getQuantite());
            stockRepository.save(stock);

            // Mouvement stock ENTREE
            MouvementStock mouvement = new MouvementStock();
            mouvement.setTypeMouvement("ENTREE");
            mouvement.setQuantite(ligneReq.getQuantite());
            mouvement.setProduit(produit);
            mouvement.setMagasin(magasin);
            mouvement.setUtilisateur(utilisateur);
            mouvement.setObservation("Approvisionnement");
            mouvementStockRepository.save(mouvement);
        }

        appro = approvisionnementRepository.save(appro);
        return mapToResponseDTO(appro);
    }

    private ApprovisionnementResponseDTO mapToResponseDTO(Approvisionnement a) {
        List<LigneApprovisionnementResponse> lignes = a.getLignesApprovisionnement().stream()
                .map(l -> LigneApprovisionnementResponse.builder()
                        .produitNom(l.getProduit().getNom())
                        .quantite(l.getQuantite())
                        .prixAchat(l.getPrixAchat())
                        .sousTotal(l.getSousTotal())
                        .build())
                .toList();

        return ApprovisionnementResponseDTO.builder()
                .id(a.getId())
                .dateApprovisionnement(a.getDateApprovisionnement())
                .montantTotal(a.getMontantTotal())
                .statut(a.getStatut())
                .fournisseurNom(a.getFournisseur().getNom())
                .magasinNom(a.getMagasin().getNom())
                .lignes(lignes)
                .build();
    }

    public List<ApprovisionnementResponseDTO> listerParMagasin(Long magasinId) {
        return approvisionnementRepository.findByMagasinIdOrderByDateApprovisionnementDesc(magasinId)
                .stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }
}