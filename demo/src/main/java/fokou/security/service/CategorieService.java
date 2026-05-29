package fokou.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.gestion_stock.dto.CategorieDto;
import com.example.gestion_stock.model.entity.Categorie;
import com.example.gestion_stock.repository.CategorieRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieDto creer(CategorieDto dto) {
        if (categorieRepository.existsByLibelle(dto.getLibelle())) {
            throw new RuntimeException("Une catégorie avec ce libellé existe déjà");
        }
        Categorie c = new Categorie();
        c.setLibelle(dto.getLibelle());
        c.setDescription(dto.getDescription());
        c = categorieRepository.save(c);
        return mapToDto(c);
    }

    public List<CategorieDto> lister() {
        return categorieRepository.findAll()
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public CategorieDto modifier(Long id, CategorieDto dto) {
        Categorie c = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        c.setLibelle(dto.getLibelle());
        c.setDescription(dto.getDescription());
        return mapToDto(categorieRepository.save(c));
    }

    public CategorieDto consulter(Long id) {
        Categorie c = categorieRepository.findById(id).orElseThrow(() -> new RuntimeException("Catégorie introuvable"));
        return mapToDto(c);
    }
    public void supprimer(Long id) {
        if (!categorieRepository.existsById(id)) throw new RuntimeException("Catégorie introuvable");
        categorieRepository.deleteById(id);
    }

    private CategorieDto mapToDto(Categorie c) {
        CategorieDto dto = new CategorieDto();
        dto.setId(c.getId());
        dto.setLibelle(c.getLibelle());
        dto.setDescription(c.getDescription());
        return dto;
    }

    public List<CategorieDto> listerInactifs() {
        return categorieRepository.findAllInactifs().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void restaurer(Long id) {
        if (categorieRepository.countByIdInactif(id) == 0) {
            throw new RuntimeException("Catégorie introuvable parmi les éléments supprimés");
        }
        categorieRepository.restaurer(id);
    }
}