package zti.projekt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.projekt.backend.model.Role;

import java.util.Optional;

/**
 * Repozytorium odpowiedzialne za role uzytkownikow.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByAuthority(String authority);
}
