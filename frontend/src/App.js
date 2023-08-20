import Sidebar from "./Sidebar";
import Search from "./Search";
import MainBody from "./MainBody";
import { useState } from "react";
import { getToken } from "./utils";
import axios from "axios";
import { arrayMoveImmutable } from 'array-move';
import { url } from "./utils";


function App() {
  const [entries, setEntries] = useState([]);
  const [ranking, setRanking] = useState(null);
  const [title, setTitle] = useState("");
  const [searchActive, setSearchActive] = useState(false);


  const onSortEnd = ({ oldIndex, newIndex }) => {
    setEntries(prevItem => (arrayMoveImmutable(prevItem, oldIndex, newIndex)));

    axios.put(url + '/ranking/'+ranking + "/" + oldIndex++ +"/" + newIndex++).then(response => {
      }).catch(error => {
        console.log("Something went wrong. Please try again later.");
      });
  };

  const getEntries = (id) => {
    const token = getToken();
      axios.defaults.headers.common = {'Authorization': `Bearer ${token}`};

      axios.get(url + '/ranking/'+id+'/sorted').then(response => {
        setEntries(response.data);
      }).catch(error => {
        console.log("Something went wrong????");
      });
  };

  const addEntry = (spotifyId) => {
    axios.post(url + '/ranking/'+ranking, { spotifyId: spotifyId }).then(response => {
      updateRank(ranking, title);
    }).catch(error => {
      console.log("Couldn't add entry.");
    });
  };

  const updateRank = (id, title) =>{
    setRanking(id);
    getEntries(id);
    setTitle(title);
  };

  const deleteEntry = (id, spotifyId) =>{
    axios.delete(url + '/ranking/'+id+"/"+spotifyId).then(response => {
      updateRank(ranking, title);
    }).catch(error => {
      console.log("Couldn't delete entry.");
    });
  };

  const userLogged = (isLogged) =>{
    setSearchActive(isLogged);
    if(!isLogged){
      setEntries([]);
      setTitle("");
      setRanking(null);
    };
  };

  return (
    <div className="App">
      <Sidebar updateRank={updateRank} userLogged={userLogged}/>
      <MainBody entries={entries} title={title} rankingId={ranking} deleteEntry={deleteEntry} bodyActive={searchActive} onSortEnd={onSortEnd}/>
      <Search addEntry={addEntry} searchActive={searchActive}/>
    </div>
  );
};

export default App;
