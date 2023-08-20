package zti.projekt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.projekt.backend.model.ApplicationUser;
import zti.projekt.backend.model.Ranking;

/**
 * Repozytorium odpowiedzialne za rankingi
 */
@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    /**
     * Znajdowanie wszystkich rankingow uzytkownika
     * @param user Uzytkownik
     * @return Wszystkie rankingi tego uzytkownika
     */
    Iterable<Ranking> findByCreator(ApplicationUser user);
}
