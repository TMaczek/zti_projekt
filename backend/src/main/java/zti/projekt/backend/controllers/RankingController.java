package zti.projekt.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zti.projekt.backend.dtos.EntryDTO;
import zti.projekt.backend.dtos.EntryDataDTO;
import zti.projekt.backend.dtos.RankingDTO;
import zti.projekt.backend.model.Entry;
import zti.projekt.backend.model.Ranking;
import zti.projekt.backend.services.RankingService;
import zti.projekt.backend.services.SpotifyService;

import java.security.Principal;
import java.util.List;

/**
 * Kontroler odpowiedzialny za zadania dotyczace rankingow, szukania albumow, zarzadzania miejscami rankingu
 */
@RestController
@RequestMapping("/ranking")
@CrossOrigin("*")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private SpotifyService spotifyService;

    /**
     * Metoda odpowiedzialna za endpoint znajdowania wszystkich rankingów.
     * @return Wszystkie rankingi z bazy (wszystkich uzytkownikow!).
     */
    @CrossOrigin("**")
    @GetMapping("/all")
    public Iterable<Ranking> findAll(){
        return rankingService.getAll();
    }

    /**
     * Metoda odpowiedzialna za enpoint znajdowania rankingu.
     * @param ranking id rankingu, którego szukamy
     * @param principal Dane zalogowanego uzytkownika
     * @return Ranking jeśli jego właścicielem jest zalogowany uzytkownik, null w przeciwnym wypadku.
     */
    @CrossOrigin("**")
    @GetMapping("/{id}")
    public Ranking findById(@PathVariable("id") Ranking ranking, Principal principal)
    {
        if(principal.getName().equals(ranking.getCreator().getUsername())) // do zmiany?
            return ranking;
        else
            return null;
    }

    /**
     * Metoda odpowiedzialna za endpoint dodawania rankingu.
     * @param body Ciało requestu.
     * @return Dane dodanego rankingu (w razie powodzenia) lub inna odpowiedz (w razie niepowodzenia).
     */
    @CrossOrigin("**")
    @PostMapping
    public ResponseEntity<?> addRanking(@RequestBody RankingDTO body) {
        return ResponseEntity.ok(rankingService.addRanking(body.getUsername(), body.getRankingName()));
    }

    /**
     * Metoda odpowiedzialna za enpoint dodawania pozycji do rankingu.
     * @param ranking Ranking do ktorego dodajemy pozycje
     * @param body Cialo z danymi pozycji.
     * @return Dane dodanego albumu lub powiadomienie, że album już jest w rankingu.
     */
    @CrossOrigin("**")
    @PostMapping("{id}")
    public ResponseEntity<?> addEntry(@PathVariable("id") Ranking ranking, @RequestBody EntryDTO body) {
        if(ranking.checkIfAlbumIsAdded(body.getSpotifyId())){
            return ResponseEntity.ok("Album already added!");
        }
        Entry entry = new Entry(body.getSpotifyId(), ranking.getEntries().size() +1L);
        return ResponseEntity.ok(rankingService.addEntry(entry, ranking.getRankingId()));
    }

    /**
     * Metoda odpowiedzialna za enpoint otrzymania pozycji rankingu
     * @param rankingId Id rankingu, ktorego pozycje chcemy otrzymac
     * @return Posortowana lista pozycji w rankingu
     */
    @CrossOrigin("**")
    @GetMapping("{id}/sorted")
    public Iterable<EntryDataDTO> getSortedEntries(@PathVariable("id") Long rankingId){
        return rankingService.getEntries(rankingId);
    }

    /**
     * Metoda odpowiedzialna za enpoint pobierania pojedynczej pozycji rankingu
     * @param rankingId Id rankingu, na ktorym dzialamy
     * @param entryId Id pozycji, ktora chcemy otrzymac
     * @return Pozycja o zadanym id
     */
    @CrossOrigin("**")
    @GetMapping("{id}/{entryId}")
    public Entry getEntry(@PathVariable("id") Long rankingId, @PathVariable("id") Long entryId){
        return rankingService.getEntry(entryId);
    }

    /**
     * Metoda odpowiedzialna za enpoint szukania albumow w bazie Spotify
     * @param q Query po ktorym szukamy albumu
     * @return Lista znalezionych albumow
     */
    @CrossOrigin("**")
    @GetMapping("/search/{q}")
    public List<EntryDataDTO> searchForAlbums(@PathVariable String q){
        return spotifyService.search(q);
    }

    /**
     * Metoda odpowiedzialna za enpoint znajdowania wszyskich rankingow uzytkownika
     * @param principal Zalogowany uzytkownik
     * @return Lista wszystkich rankingow zalogowanego uzytkownika
     */
    @CrossOrigin("**")
    @GetMapping("/user")
    public Iterable<Ranking> findUserRankings(Principal principal)
    {
        return rankingService.getUserRankings(principal.getName());

    }

    /**
     * Metoda odpowiedzialna za enpoint przesuwania pozycji w rankigu
     * Ze względu na indeksowanie w React, zaczynamy liczenie od pozycji 0, nie od 1!
     * @param id Id rankingu, w ktorym zmieniamy pozycje
     * @param oldpos Pozycja albumu przed zmiana
     * @param newpos Pozycja albumu po zmianie
     * @return Odpowiedz zawierajaca zaktualizowana liste pozycji lub inny response w razie bledu
     */
    @CrossOrigin("**")
    @PutMapping("/{id}/{oldpos}/{newpos}")
    public ResponseEntity<?> moveEntry(@PathVariable Long id, @PathVariable Long oldpos, @PathVariable Long newpos){
        return ResponseEntity.ok(rankingService.moveEntry(id, oldpos, newpos));
    }

    /**
     * Metoda odpowiedzialna za enpoint usuwania rankingu
     * @param id Id rankingu ktory chcemy usunac
     * @return Opowiedz o usunieciu rankingu
     */
    @CrossOrigin("**")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRanking(@PathVariable Long id){
        rankingService.deleteRanking(id);
        return ResponseEntity.ok("Ranking deleted");
    }

    /**
     * Metoda odpowiedzialna za enpoint usuwania pozycji z rankingu
     * @param id Id rankingu z ktorego usuwamy
     * @param entry SpotifyId albumu, ktory chcemy usunac
     * @return Odpowiedz o usunieciu rankingu
     */
    @CrossOrigin("**")
    @DeleteMapping("/{id}/{entry}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id, @PathVariable String entry)
    {
        rankingService.deleteEntry(id, entry);

        return ResponseEntity.ok("Entry deleted");
    }

}
