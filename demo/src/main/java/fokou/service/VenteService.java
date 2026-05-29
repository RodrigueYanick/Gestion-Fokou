package fokou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gestion_stock.dto.LigneVenteRequest;
import com.example.gestion_stock.dto.LigneVenteResponse;
import com.example.gestion_stock.dto.VenteRequestDTO;
import com.example.gestion_stock.dto.VenteResponseDTO;
import com.example.gestion_stock.model.entity.Client;
import com.example.gestion_stock.model.entity.LigneVente;
import com.example.gestion_stock.model.entity.Magasin;
import com.example.gestion_stock.model.entity.MouvementStock;
import com.example.gestion_stock.model.entity.Produit;
import com.example.gestion_stock.model.entity.Stock;
import com.example.gestion_stock.model.entity.Utilisateur;
import com.example.gestion_stock.model.entity.Vente;
import com.example.gestion_stock.repository.ClientRepository;
import com.example.gestion_stock.repository.MagasinRepository;
import com.example.gestion_stock.repository.MouvementStockRepository;
import com.example.gestion_stock.repository.ProduitRepository;
import com.example.gestion_stock.repository.StockRepository;
import com.example.gestion_stock.repository.UtilisateurRepository;
import com.example.gestion_stock.repository.VenteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VenteService {

    private final VenteRepository venteRepository;
    private final ProduitRepository produitRepository;
    private final StockRepository stockRepository;
    private final MouvementStockRepository mouvementStockRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ClientRepository clientRepository;
    private final MagasinRepository magasinRepository;

    public VenteResponseDTO creerVente(VenteRequestDTO dto, String loginVendeur) {
        Utilisateur vendeur = utilisateurRepository.findByLogin(loginVendeur)
                .orElseThrow(() -> new RuntimeException("Vendeur non trouvé"));
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));
        Magasin magasin = magasinRepository.findById(dto.getMagasinId())
                .orElseThrow(() -> new RuntimeException("Magasin introuvable"));

        Vente vente = new Vente();
        vente.setDateVente(LocalDateTime.now());
        vente.setModePaiement(dto.getModePaiement());
        vente.setStatutVente("VALIDEE");
        vente.setVendeur(vendeur);
        vente.setClient(client);
        vente.setMagasin(magasin);
        vente.setMontantTotal(0);

        List<LigneVente> lignes = new ArrayList<>();

        for (LigneVenteRequest ligneReq : dto.getLignes()) {
            Produit produit = produitRepository.findById(ligneReq.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit ID " + ligneReq.getProduitId() + " introuvable"));

            Stock stock = stockRepository.findByProduitIdAndMagasinId(produit.getId(), magasin.getId())
                    .orElseThrow(() -> new RuntimeException("Stock non initialisé pour le produit " + produit.getNom() + " dans ce magasin"));
            if (stock.getQuantiteDisponible() < ligneReq.getQuantite()) {
                throw new RuntimeException("Stock insuffisant pour " + produit.getNom() + " (demandé: " + ligneReq.getQuantite() + ", disponible: " + stock.getQuantiteDisponible() + ")");
            }

            // Création ligne vente
            LigneVente ligne = new LigneVente();
            ligne.setProduit(produit);
            ligne.setQuantite(ligneReq.getQuantite());
            ligne.setPrixUnitaire(produit.getPrixUnitaire());
            double sousTotal = ligneReq.getQuantite() * produit.getPrixUnitaire();
            ligne.setSousTotal(sousTotal);
            vente.addLigneVente(ligne);
            vente.setMontantTotal(vente.getMontantTotal() + sousTotal);

            // Mise à jour du stock
            stock.setQuantiteDisponible(stock.getQuantiteDisponible() - ligneReq.getQuantite());
            stockRepository.save(stock);

            // Enregistrement mouvement stock (SORTIE)
            MouvementStock mouvement = new MouvementStock();
            mouvement.setTypeMouvement("SORTIE");
            mouvement.setQuantite(ligneReq.getQuantite());
            mouvement.setProduit(produit);
            mouvement.setMagasin(magasin);
            mouvement.setUtilisateur(vendeur);
            mouvement.setObservation("Vente n° (sera complété après save)");
            mouvementStockRepository.save(mouvement);
        }

        vente = venteRepository.save(vente);
        // On peut mettre à jour l'observation du mouvement avec l'ID de la vente, ici on laisse simple
        return mapToResponseDTO(vente);
    }

    public List<VenteResponseDTO> listerParMagasin(Long magasinId) {
        return venteRepository.findByMagasinIdOrderByDateVenteDesc(magasinId)
                .stream().map(this::mapToResponseDTO).toList();
    }

    private VenteResponseDTO mapToResponseDTO(Vente v) {
        List<LigneVenteResponse> lignes = v.getLignesVente().stream()
                .map(l -> LigneVenteResponse.builder()
                        .produitNom(l.getProduit().getNom())
                        .quantite(l.getQuantite())
                        .prixUnitaire(l.getPrixUnitaire())
                        .sousTotal(l.getSousTotal())
                        .build())
                .toList();

        return VenteResponseDTO.builder()
                .id(v.getId())
                .dateVente(v.getDateVente())
                .montantTotal(v.getMontantTotal())
                .modePaiement(v.getModePaiement())
                .statutVente(v.getStatutVente())
                .vendeurNom(v.getVendeur().getNom() + " " + v.getVendeur().getPrenom())
                .clientNom(v.getClient().getNom() + " " + (v.getClient().getPrenom() != null ? v.getClient().getPrenom() : ""))
                .magasinNom(v.getMagasin().getNom())
                .lignes(lignes)
                .build();
    }
}