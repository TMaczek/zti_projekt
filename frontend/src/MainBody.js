import { useState } from "react";
import SortableList from './SortableList';

const MainBody = ({entries, title, rankingId, deleteEntry, bodyActive, onSortEnd}) => {
    const [message, setMessage] = useState("");

    if(bodyActive && title == ""){
        return (
            <div className="mainBody">
                {message}
                <p>Select ranking to add albums.</p>
            </div>
        );
    } else if (bodyActive){
        return (
            <div className="mainBody">
                {message}
                <h1>{title}</h1>
                <SortableList entries={entries} rankingId={rankingId} deleteEntry={deleteEntry} onSortEnd={onSortEnd} />
            </div>
        );
    } else{
        return (
            <div className="bodyNotActive">
                <p>Login to view rankings!</p>
            </div>
        )
    }

};
 
export default MainBody;
