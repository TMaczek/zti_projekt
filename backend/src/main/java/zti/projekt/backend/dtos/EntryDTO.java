package zti.projekt.backend.dtos;

/**
 * DTO obiektu przekazywanego gdy dodajemy jeden z wyszukanych albumow do rankingu.
 * Zawiera on jedynie spotifyId, gdyz tylko ono jest wtedy potrzebne.
 */
public class EntryDTO {
    public EntryDTO() {
    }

    private String spotifyId;

    public EntryDTO(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
}
