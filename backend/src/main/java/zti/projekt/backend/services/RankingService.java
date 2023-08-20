package zti.projekt.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zti.projekt.backend.dtos.EntryDataDTO;
import zti.projekt.backend.model.ApplicationUser;
import zti.projekt.backend.model.Entry;
import zti.projekt.backend.model.Ranking;
import zti.projekt.backend.repository.EntryRepository;
import zti.projekt.backend.repository.RankingRepository;
import zti.projekt.backend.repository.UserRepository;
import zti.projekt.backend.utils.EntryComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Serwis odpowiedzialny za operacje na rankingach
 */
@Service
public class RankingService {
    @Autowired
    private RankingRepository rankingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private SpotifyService spotifyService;

    /**
     * Metoda zwracajaca wszystkie rankingi
     * @return Iterable z wszystkimi rankingami w bazie
     */
    public Iterable<Ranking> getAll() {
        return rankingRepository.findAll();
    }

    /**
     * Metoda dodająca nowy ranking do bazy
     * @param username Nazwa uzytkownika dodajacego ranking
     * @param rankingName Nazwa tworzonego rankingu
     * @return Obiekt stworzonego rankingu.
     */
    public Ranking addRanking(String username, String rankingName) {
        return rankingRepository.save(new Ranking(rankingName, userRepository.findByUsername(username).get()));
    }

    /**
     * Metoda usuwajaca ranking
     * @param rankingId Id rankingu, ktory chcemy usunac
     */
    public void deleteRanking(Long rankingId){
        rankingRepository.deleteById(rankingId);
    }

    /**
     * Metoda usuwajaca pozycje z rankingu
     * @param rankingId Id rankingu z ktorego usuwamy
     * @param spotifyId SpotifyId albumu, ktory usuwamy
     */
    public void deleteEntry(Long rankingId, String spotifyId){
        Ranking ranking = rankingRepository.getReferenceById(rankingId);
        ranking.getEntries().sort(new EntryComparator());
        List<Entry> entries = ranking.getEntries();

        Long position = 0L;
        Integer size =  entries.size();
        if (size==1){
            entries.clear();
        }

        List<Entry> updated = new ArrayList<Entry>();

        for(Entry entry: entries){
                if (entry.getSpotifyId().equals(spotifyId)){
                    position = entry.getPosition();
                }
                else{
                    updated.add(entry);
                }
        }

        ranking.setEntries(updated);
        ranking.getEntries().sort(new EntryComparator());
        entries = ranking.getEntries();

        if (!size.equals(position.intValue()))
        {
            for(Entry entry: entries){
                if (entry.getPosition() > position){
                    entry.setPosition(entry.getPosition()-1L);
                }
            }
        }
        rankingRepository.save(ranking);
    }

    /**
     * Metotda dodajaca pozycje do rankingu
     * @param entry Dodawana pozycja
     * @param rankingId Id rankingu do ktorego dodajemy
     * @return Obiekt dodanej pozycji
     */
    public Entry addEntry(Entry entry, Long rankingId) {
        Ranking ranking = rankingRepository.getReferenceById(rankingId);
        ranking.addEntryToRanking(entry);
        rankingRepository.save(ranking);
        return entry;
    }

    /**
     * Metoda zwracajaca wszystkie pozycje z rankingu.
     * Pozycje sa wczesniej sortowane wg numeru pozycji
     * @param rankingId Id rankingu, ktorego pozycje chcemy otrzymac
     * @return Lista zawierajaca posortowane pozycje z rankingu
     */
    public Iterable<EntryDataDTO> getEntries(Long rankingId) {
        rankingRepository.getReferenceById(rankingId).getEntries().sort(new EntryComparator());
        List<Entry> entries = rankingRepository.getReferenceById(rankingId).getEntries();
        return spotifyService.getFromList(entries);
    }

    /**
     * Metoda pobierajaca pojedyncza pozycje
     * @param entryId Id pozycji, ktora chcemy pobrac
     * @return Obiekt pobranej pozycji
     */
    public Entry getEntry(Long entryId) {
        return entryRepository.getReferenceById(entryId);
    }

    /**
     * Metoda pobierajaca wszyskie rankingi uzytkownika
     * @param username Nazwa uzytkownika, ktorego rankigi chcemy
     * @return Iterable z rankingami uzytkownika
     */
    public Iterable<Ranking> getUserRankings(String username) {
        ApplicationUser user = userRepository.findByUsername(username).get();
        return rankingRepository.findByCreator(user);
    }

    /**
     * Metoda przesuwania pozycji w rankingu
     * Poza przesunieciem reprezentowanym przez oldpos i newpos, wszystkie pozycje o numerach pomiędzy są przesuwane
     * odpowiedno o 1 pozycję w górę lub 1 pozycję w dół
     * @param rankingId Id edytowanego rankingu
     * @param oldpos Stary numer pozycji
     * @param newpos Nowy numer pozycji
     * @return Lista pozycji po zmianach
     */
    public List<Entry> moveEntry(Long rankingId, Long oldpos, Long newpos){ // liczymy od 1, nie 0
        Ranking ranking = rankingRepository.getReferenceById(rankingId);
        ranking.getEntries().sort(new EntryComparator());

        oldpos +=1L;
        newpos +=1L;

        List<Entry> entries = ranking.getEntries();
        for(Entry entry: entries){
            if (oldpos > newpos) // ppozycja w gore, reszta w dol
            {
                if (entry.getPosition() < oldpos && entry.getPosition() >=newpos)
                {
                    entry.setPosition(entry.getPosition() + 1L);
                }
                else if (entry.getPosition().equals(oldpos)) {
                    entry.setPosition(newpos);
                }
            }
            else if (oldpos < newpos) // ppozycja w dol, reszta w gore
            {
                if (entry.getPosition() > oldpos && entry.getPosition() <=newpos)
                {
                    entry.setPosition(entry.getPosition() - 1L);
                }
                else if (entry.getPosition().equals(oldpos)) {
                    entry.setPosition(newpos);
                }
            }
        }
        rankingRepository.save(ranking);
        ranking.getEntries().sort(new EntryComparator());
        rankingRepository.save(ranking);
        return ranking.getEntries();
    }
}
