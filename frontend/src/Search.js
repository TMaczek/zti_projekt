import axios from "axios";
import { useState } from "react";
import { getToken } from "./utils";
import SearchAlbum from "./SearchAlbum";
import { url } from "./utils";

const Search = ({addEntry, searchActive}) => {
  const query = useFormInput('katy perry');
  const [albums, setAlbums] = useState([]);

  const handleSearch = ()=>{
    const token = getToken();
    const config = { headers: { 'Authorization': `Bearer ${token}`}};
    const bodyParameters = { key: "value"};
    axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

    axios.get(url + '/ranking/search/'+query.value, bodyParameters, config).then(response => {
      setAlbums(response.data);
    }).catch(error => {
      console.log("Something went wrong. Please try again later.");
    });
  };

  if (searchActive){
    return ( 
      <div className="search">
        <div>
          Search<br />
          <input type="text" value={query.value} onChange={query.onChange} autoComplete="new-query" />
        </div>
        <input type="button" value='Search' onClick={handleSearch}/><br />

        {albums.map((album) => {
          return (
            <SearchAlbum album={album} key={album.spotifyId} addEntry={addEntry}/>
          )
        })}
        </div>  
    );
    } else{
      return (
        <div className="searchNotActive">
          <p>Login to search albums!</p>
        </div>
      )
    }
};
 
const useFormInput = initialValue => {
  const [value, setValue] = useState(initialValue);
   
  const handleChange = e => {
    setValue(e.target.value);
  };

  return {
    value,
    onChange: handleChange
  };
};

export default Search;