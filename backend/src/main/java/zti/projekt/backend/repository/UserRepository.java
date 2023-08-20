package zti.projekt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.projekt.backend.model.ApplicationUser;

import java.util.Optional;

/**
 * Repozytorium odpowiedzialne za uzytkownikow aplikacji.
 */
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    /**
     * Znadowanie uzytkonika po jego nazwie
     * @param username Nazwa ozytkownika
     * @return Obiekt uzytkownika (jesli istnieje)
     */
    Optional<ApplicationUser> findByUsername(String username);
}
