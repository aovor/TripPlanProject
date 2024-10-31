import React, {useState} from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './Search.css';

function SearchPw() {

  const [id, setId] = useState('')

  const handleClick = () => {

    axios.post('https://localhost:8080/SearchPw', 
      {
        username: id,   
      },
      {
        withCredentials: true // 쿠키
      }
    )
    .then(response => {
      
      console.log('비밀번호 찾기 성공:', response);
    })
    .catch(error => {
      console.error('비밀번호 찾기 실패:', error);
    });
  };

  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="box">
                <div className = "login-title">
                  비밀번호 찾기
                </div>
 
                </div>
          </header>
        </div>
    );
  }
  
  export default SearchPw;

