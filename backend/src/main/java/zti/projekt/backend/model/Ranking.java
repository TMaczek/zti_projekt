package zti.projekt.backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentuja obiekt rankingu w bazie danych
 */
@Entity
@Table(name="rankings")
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ranking_id")
    private Long rankingId;

    private String rankingName;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private ApplicationUser creator;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Entry> entries;

    public Ranking() {
        super();
        this.entries = new ArrayList<Entry>();
    }

    public Ranking(String rankingName, ApplicationUser creator) {
        this.rankingName = rankingName;
        this.creator = creator;
        this.entries = new ArrayList<Entry>();
    }

    public Long getRankingId() {
        return rankingId;
    }

    public ApplicationUser getCreator() {
        return creator;
    }

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String rankingName) {
        this.rankingName = rankingName;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    /**
     * Metoda dodajaca nową pozycję do rankingu
     * @param entry Pozycja którą chcemy dodać
     */
    public void addEntryToRanking(Entry entry){
        entries.add(entry);
    }

    /**
     * Metoda sprawdzająca, czy album już jest dodany do tego rankingu
     * @param spotifyId SpotifyId albumu, ktory sprawdzamy
     * @return True jesli juz jest w rankingu, false jesli nie
     */
    public Boolean checkIfAlbumIsAdded(String spotifyId){
        for (Entry entry: entries){
            if (entry.getSpotifyId().equals(spotifyId)){
                return true;
            }
        }
        return false;
    }
}
