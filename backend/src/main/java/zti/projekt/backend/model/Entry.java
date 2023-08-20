package zti.projekt.backend.model;

import jakarta.persistence.*;

/**
 * Klasa reprezentujaca obiekt entry - pozycji w bazie danych
 */
@Entity
@Table(name="entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="entry_id")
    private Long entryId;

    private String spotifyId;
    private Long position;

    public Entry() {
        super();
    }

    public Entry(String spotifyId, Long position) {
        this.spotifyId = spotifyId;
        this.position = position;
    }

    public Entry(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public Long getEntryId() {
        return entryId;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public Long getPosition() {
        return position;
    }


    public void setPosition(Long position) {
        this.position = position;
    }


}
