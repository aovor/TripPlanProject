import React, {useState} from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './Signup.css';

function Signup() {

  const [id, setId] = useState('')
  const [pw, setPw] = useState('')

  const handleClick = () => {
    alert('로그인 버튼 눌림');

    axios.post('https://localhost:8080/Signup', 
      {
        username: id,  
        password: pw    
      },
      {
        withCredentials: true // 쿠키
      }
    )
    .then(response => {
      alert('로그인');
      console.log('로그인 성공:', response);
    })
    .catch(error => {
      console.error('로그인 실패:', error);
    });
  };

  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="box">
                <div className = "login-title">
                  회원가입
                </div>
 
                </div>
          </header>
        </div>
    );
  }
  
  export default Signup;

