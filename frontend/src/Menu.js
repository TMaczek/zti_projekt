import { useState } from "react";

const Menu = ({addRanking}) => {
  const newRankingName = useFormInput('new ranking');

  return (
    <div className="menu">
      <input type="text" value={newRankingName.value} onChange={newRankingName.onChange} autoComplete="new-ranking" />
      <input type="button" value='Add new ranking' onClick={()=>addRanking(newRankingName)}/><br />

    </div>
  );
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

export default Menu;