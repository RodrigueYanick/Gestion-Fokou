package fokou.security.service;
package com.example.gestion_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fokou.dto.UtilisateurDTO;
import fokou.entity.Magasin;
import fokou.entity.Role;
import fokou.entity.Utilisateur;
import fokou.repository.MagasinRepository;
import fokou.repository.RoleRepository;
import fokou.repository.UtilisateurRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final MagasinRepository magasinRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurDTO creer(UtilisateurDTO dto) {
        if (utilisateurRepository.existsByLogin(dto.getLogin())) {
            throw new RuntimeException("Ce login est déjà utilisé");
        }
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rôle introuvable"));

        Utilisateur u = new Utilisateur();
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setEmail(dto.getEmail());
        u.setTelephone(dto.getTelephone());
        u.setLogin(dto.getLogin());
        u.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        u.setStatut("ACTIF");
        u.setRole(role);
        if (dto.getMagasinId() != null) {
            Magasin magasin = magasinRepository.findById(dto.getMagasinId())
                    .orElseThrow(() -> new RuntimeException("Magasin introuvable"));
            u.setMagasin(magasin);
        }
        u = utilisateurRepository.save(u);
        return mapToDTO(u);
    }

    public List<UtilisateurDTO> lister() {
        return utilisateurRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private UtilisateurDTO mapToDTO(Utilisateur u) {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(u.getId());
        dto.setNom(u.getNom());
        dto.setPrenom(u.getPrenom());
        dto.setEmail(u.getEmail());
        dto.setTelephone(u.getTelephone());
        dto.setLogin(u.getLogin());
        dto.setRoleId(u.getRole().getId());
        dto.setMagasinId(u.getMagasin() != null ? u.getMagasin().getId() : null);
        return dto;
    }

    public UtilisateurDTO consulter(Long id) {
        return mapToDTO(utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable")));
    }

    public UtilisateurDTO modifier(Long id, UtilisateurDTO dto) {
        Utilisateur u = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setEmail(dto.getEmail());
        u.setTelephone(dto.getTelephone());
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().isBlank()) {
            u.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }
        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Rôle introuvable"));
            u.setRole(role);
        }
        if (dto.getMagasinId() != null) {
            Magasin magasin = magasinRepository.findById(dto.getMagasinId())
                    .orElseThrow(() -> new RuntimeException("Magasin introuvable"));
            u.setMagasin(magasin);
        } else {
            u.setMagasin(null);
        }
        return mapToDTO(utilisateurRepository.save(u));
    }

    public void supprimer(Long id) {
        if (!utilisateurRepository.existsById(id)) throw new RuntimeException("Utilisateur introuvable");
        utilisateurRepository.deleteById(id);
    }

    public List<UtilisateurDTO> listerInactifs() {
        return utilisateurRepository.findAllInactifs().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public void restaurer(Long id) {
        if (utilisateurRepository.countByIdInactif(id) == 0) {
            throw new RuntimeException("Utilisateur introuvable parmi les éléments supprimés");
        }
        utilisateurRepository.restaurer(id);
    }
}