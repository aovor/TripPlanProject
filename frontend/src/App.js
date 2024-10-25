import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; 
import './App.css';


import Main from './Main/Main'; 
import Login from './Login/Login';
import Signup from './Signup/Signup';
import SearchPw from './Login/SearchPw'

function App() {
  return (
    <Router>
        <Routes>
          <Route path="/" element={<Main />} />
          <Route path="/Login" element={<Login  />} />
          <Route path='/Signup' element={<Signup/>} />
          <Route path='/SearchPw' element = {<SearchPw/>} />
        </Routes>
    </Router>
  );
}

export default App;
