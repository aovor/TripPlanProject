import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; 
import './App.css';


import Main from './Main/Main'; 
import Login from './Login/Login';

/* 회원가입 */
import Signup from './Signup/Signup';
import ComSignup from './Signup/ComSignup';

/* 비밀번호 찾기 */
import SearchPw from './Login/Searchpw/SearchPw';
import ResetPw from './Login/Searchpw/ResetPw';
import ComPw from './Login/Searchpw/ComPw';

function App() {
  return (
    <Router>
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/Login" element={<Login  />} />
          <Route path='/Signup' element={<Signup/>} />
          <Route path='/ComSignup' element = {<ComSignup/>} />
          
          {/*비밀번호 찾기 */}
          <Route path='/SearchPw' element = {<SearchPw/>} />
          <Route path='/ResetPw' element = {<ResetPw/>} />
          <Route path='/ComPw' element = {<ComPw/>} />
          
        </Routes>
    </Router>
  );
}

export default App;
