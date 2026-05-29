package fokou.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fokou.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNomRole(String nomRole);
    
}
