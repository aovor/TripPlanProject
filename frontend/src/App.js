import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; 
import './App.css';


import Main from './Main/Main'; 

function App() {
  return (
    <Router>
        <Routes>
          <Route path="/" element={<Main />} />
        </Routes>
    </Router>
  );
}

export default App;
