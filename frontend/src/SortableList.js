import SortableItem from './SortableItem';
import { SortableContainer } from 'react-sortable-hoc';

const SortableList = ({entries, rankingId, deleteEntry}) => {
  return (
    <ol>
      {entries.map((album, index) => (
        <SortableItem key={`item-${index}`} index={index} album={album} rankingId={rankingId} deleteEntry={deleteEntry} />
      ))}
    </ol>
  );
};
 
export default SortableContainer(SortableList); 