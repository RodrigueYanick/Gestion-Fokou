package fokou.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gestion_stock.dto.ProduitDTO;
import com.example.gestion_stock.model.entity.Categorie;
import com.example.gestion_stock.model.entity.Produit;
import com.example.gestion_stock.repository.CategorieRepository;
import com.example.gestion_stock.repository.ProduitRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;

    public ProduitDTO ajouter(ProduitDTO dto) {
        if (produitRepository.existsByReference(dto.getReference())) {
            throw new RuntimeException("La référence produit existe déjà");
        }
        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        Produit p = new Produit();
        p.setReference(dto.getReference());
        p.setNom(dto.getNom());
        p.setDescription(dto.getDescription());
        p.setPrixUnitaire(dto.getPrixUnitaire());
        p.setUniteMesure(dto.getUniteMesure());
        p.setSeuilAlerte(dto.getSeuilAlerte());
        p.setStatut("ACTIF");
        p.setCategorie(cat);
        p = produitRepository.save(p);
        return mapToDTO(p);
    }

    public ProduitDTO modifier(Long id, ProduitDTO dto) {
        Produit p = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));
        Categorie cat = categorieRepository.findById(dto.getCategorieId())
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        p.setReference(dto.getReference());
        p.setNom(dto.getNom());
        p.setDescription(dto.getDescription());
        p.setPrixUnitaire(dto.getPrixUnitaire());
        p.setUniteMesure(dto.getUniteMesure());
        p.setSeuilAlerte(dto.getSeuilAlerte());
        p.setStatut(dto.getStatut());
        p.setCategorie(cat);
        return mapToDTO(produitRepository.save(p));
    }

    public List<ProduitDTO> lister() {
        return produitRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProduitDTO consulter(Long id) {
        return mapToDTO(produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable")));
    }

    public void supprimer(Long id) {
        if (!produitRepository.existsById(id)) throw new RuntimeException("Produit introuvable");
        produitRepository.deleteById(id);
    }

    public List<ProduitDTO> rechercherParNom(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ProduitDTO mapToDTO(Produit p) {
        ProduitDTO dto = new ProduitDTO();
        dto.setId(p.getId());
        dto.setReference(p.getReference());
        dto.setNom(p.getNom());
        dto.setDescription(p.getDescription());
        dto.setPrixUnitaire(p.getPrixUnitaire());
        dto.setUniteMesure(p.getUniteMesure());
        dto.setSeuilAlerte(p.getSeuilAlerte());
        dto.setStatut(p.getStatut());
        dto.setCategorieId(p.getCategorie().getId());
        dto.setCategorieLibelle(p.getCategorie().getLibelle());
        return dto;
    }

    public List<ProduitDTO> listerInactifs() {
        return produitRepository.findAllInactifs().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void restaurer(Long id) {
        if (produitRepository.countByIdInactif(id) == 0) {
            throw new RuntimeException("Produit introuvable parmi les éléments supprimés");
        }
        produitRepository.restaurer(id);
    }
}