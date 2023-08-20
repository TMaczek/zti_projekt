import Login from "./Login";

const Sidebar = ({updateRank, userLogged}) => {
    return ( 
        <div className="sidebar">
            <h1>Perfect Placements</h1>
            <Login updateRank={updateRank} userLogged={userLogged}/>
        </div>
     );
};
 
export default Sidebar;