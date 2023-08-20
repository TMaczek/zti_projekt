package zti.projekt.backend.services;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
import zti.projekt.backend.dtos.EntryDataDTO;
import zti.projekt.backend.model.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Serwis odpowiedzialny za uzywanie Spotify API
 */
@Service
public class SpotifyService {


    /**
     *     klucze są generowane na stronie https://developer.spotify.com/dashboard po stworzeniu aplikacji
     */

    @Value("${property.clientId}")
    private static String clientId = "9a0ea217cf934c278c32a9f69cf563de";

    @Value("${property.clientSecret}")
    private static String clientSecret = "1357da53f6994d198e51068d3fa56a3e";

    public SpotifyService(){
        System.out.println(clientId);
    }

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    ClientCredentials clientCredentials;

    /**
     * Metoda inicjujaca połączenie z API, pobiera Access Token, który jest aktywny 5 minut
     * @throws IOException
     * @throws ParseException
     * @throws SpotifyWebApiException
     */
    public void init() throws IOException, ParseException, SpotifyWebApiException {
        try{
            clientCredentials = clientCredentialsRequest.execute();
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        }catch (IOException | SpotifyWebApiException | ParseException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Metoda wyszukiwania albumu
     * Wyszukujemy 10 albumow (mozna inna ilosc, mozna bez limitu)
     * Z otrzymanej odpowiedzi bierzemy potrzebne nam dane do EntryDataDTO
     * @param q Nazwa albumu, ktory chcemy wyszukac
     * @return Lista znalezionych albumow
     */
    public List<EntryDataDTO> search(String q) {

        List<EntryDataDTO> results = new ArrayList<EntryDataDTO>();

        try {
            this.init();

            SearchAlbumsRequest searchAlbumsRequest = spotifyApi.searchAlbums(q).limit(10).build();
            final Paging<AlbumSimplified> albumSimplifiedPaging = searchAlbumsRequest.execute();
            AlbumSimplified[] albumy = albumSimplifiedPaging.getItems();

            for (AlbumSimplified albumSimplified : albumy) {
                results.add(new EntryDataDTO(albumSimplified.getId(),
                        albumSimplified.getImages()[0].getUrl(),
                        albumSimplified.getArtists()[0].getName(),
                        albumSimplified.getName(), 0L ));
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return results;
    }

    /**
     * Metoda pomocnicza konwertująca Entry na EntryDataDTO
     * Jest ona w tym serwisie, ponieważ w bazie przechowujemy tylko spotifyId
     * Tymczasem frontend potrzebuje powiazane z tym id artyste, tytul oraz URL okładki
     * Ta funkcja wykonuje taka konwersje
     * @param entries Lista pozycji z rankingu
     * @return Lista obiektów EntryDataDTO odpowiadajacych entries
     */
    public List<EntryDataDTO> getFromList(List<Entry> entries){
        List<EntryDataDTO> results = new ArrayList<EntryDataDTO>();

        try{
            this.init();

            for (Entry entry : entries) {
                Album album = spotifyApi.getAlbum(entry.getSpotifyId()).build().execute();
                results.add(new EntryDataDTO(album.getId(),
                        album.getImages()[0].getUrl(),
                        album.getArtists()[0].getName(),
                        album.getName(),
                        entry.getPosition() ));
            }

        }  catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return results;
    }

}
