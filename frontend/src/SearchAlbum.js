const SearchAlbum = ({album, addEntry}) => {
    return (
        <div className="searchAlbums" key={album.spotifyId}>
            <img src={album.imageUrl} alt={album.spotifyId} width='30%' /> <br />
            <p>{album.albumTitle} - {album.artist}</p>
            <input type="button" value='ADD' onClick={() => addEntry(album.spotifyId)}/><br />
        </div>
    );
};
 
export default SearchAlbum;