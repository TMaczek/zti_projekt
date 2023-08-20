import { SortableElement } from 'react-sortable-hoc';
 
const SortableItem = ({album, rankingId, deleteEntry}) => {
  return(
    <li>
      <img src={album.imageUrl} alt={album.spotifyId} width='15%' />
      <h3>{album.albumTitle} - {album.artist}</h3>
      <input type="button" value='Delete' onClick={()=> deleteEntry(rankingId, album.spotifyId)}/><br />
    </li>
  )
};
 
export default SortableElement(SortableItem);