import { useState } from "react";
import axios from "axios";
import { getToken, setUserSession, removeUserSession } from "./utils";
import Menu from "./Menu"
import { url } from "./utils";

const Login = ({updateRank, userLogged}) => {
  const username = useFormInput('');
  const password = useFormInput('');
  const [logged, setLogged] = useState(false);
  const [message, setMessage] = useState("Login or register here: ");
  const [rankings, setRankings] = useState([]);


  const handleLogin = ()=>{
    axios.post(url + '/auth/login', { username: username.value, password: password.value }).then(response => {
      setUserSession(response.data.jwt, response.data.user);
      setLogged(true);
      setMessage("Hello " + username.value  + "!");
      handleRankingList();
      userLogged(true);
    }).catch(error => {
      console.log("Something went wrong. Please try again later.");
    });
  };

  const handleLogout = () => {
    removeUserSession();
    setLogged(false);
    setMessage("Logged out.");
    setRankings([]);
    userLogged(false);
    username.reset();
    password.reset();
  };

  const handleRegister = () => {
    removeUserSession();
    axios.post(url + '/auth/register', { username: username.value, password: password.value }).then(response => {
      setUserSession(response.data.jwt, response.data.user);
      setMessage("User " + username.value + " registered sucessfully!");
    }).catch(error => {
      setMessage("Could not register.");
    });
  };

  const addRanking = (newRankingName)=> {
    const token = getToken();
    axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

    axios.post(url + '/ranking', { username: username.value, rankingName:  newRankingName.value }).then(response => {
      setMessage("New ranking added: " + newRankingName.value);
      handleRankingList();
      newRankingName.reset();
    }).catch(error => {
      console.log("Couldn't add ranking.");
    });
  };

  const handleRankingList= () =>{
    const token = getToken();
    axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

    axios.get(url + '/ranking/user').then(response => {
      setRankings(response.data);
    }).catch(error => {
      console.log("Couldn't get list of rankings.");
    });
  };


  const selectRanking = (id, rankingName) =>{
    updateRank(id, rankingName);
  };

  const deleteRanking = (id) =>{
    axios.delete(url + '/ranking/'+id).then(response => {
      handleRankingList();
      setMessage("Ranking deleted.");
    }).catch(error => {
      console.log("Couldn't delete ranking.");
    });
  };

  if(logged){
    return (
      <div className="logout">
        <p>{message}</p>
        <Menu addRanking={addRanking}/>
            
        <h2>User's rankings</h2>
        {rankings.map((ranking)=>{
          return (
            <div className="rankingList" key={ranking.rankingId}>
              <p>{ranking.rankingName}</p>
              <div className="rankingButtons">
                <input type="button" value='Select' onClick={()=> selectRanking(ranking.rankingId, ranking.rankingName)}/>
                <input type="button" value='Delete' onClick={()=> deleteRanking(ranking.rankingId)}/>
              </div>
            </div>
          )
        })}
        <br />
        <input type="button" value='Logout' onClick={handleLogout}/><br />
      </div>
    );
  } else{
    return ( 
      <div className="login">
        <p>{message}</p>
        <div>
          Username<br />
          <input type="text" id="username" value={username.value} onChange={username.onChange} autoComplete="new-username" />
        </div>
        <div style={{ marginTop: 10 }}>
          Password<br />
          <input type="password" value={password.value} onChange={password.onChange} autoComplete="new-password" />
        </div>
        <input type="button" value='Login' onClick={handleLogin}/><br />
        <input type="button" value='Register' onClick={handleRegister}/><br />
      </div>
    );
  }
};

const useFormInput = initialValue => {
  const [value, setValue] = useState(initialValue);
   
  const handleChange = e => {
    setValue(e.target.value);
  };
    
  const reset = () => {
    setValue("");
  };

  return {
    value,
    onChange: handleChange,
    reset: reset
  };
};

export default Login;