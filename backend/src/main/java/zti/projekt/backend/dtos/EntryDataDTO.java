package zti.projekt.backend.dtos;

/**
 * Data Tranfer Object przekazujacy dane pozycji w rankingu.
 */
public class EntryDataDTO {

    private String spotifyId;

    private String imageUrl;

    private String artist;

    private String albumTitle;

    private Long position;

    public EntryDataDTO() {
        super();
    }

    public EntryDataDTO(String spotifyId, String imageUrl, String artist, String albumTitle) {
        this.spotifyId = spotifyId;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.albumTitle = albumTitle;
        this.position = 0L;
    }

    public EntryDataDTO(String spotifyId, String imageUrl, String artist, String albumTitle, Long position) {
        this.spotifyId = spotifyId;
        this.imageUrl = imageUrl;
        this.artist = artist;
        this.albumTitle = albumTitle;
        this.position = position;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
}
