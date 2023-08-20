package zti.projekt.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zti.projekt.backend.model.Entry;

/**
 * Repozytorium odpowiedzialne za pozycje.
 */
@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {


}
