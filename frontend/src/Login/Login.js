import React, {useState} from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './Login.css';

function Login() {

  const [id, setId] = useState('')
  const [pw, setPw] = useState('')

  const handleClick = () => {
    

    if (!id || !pw) {
      alert("아이디와 비밀번호를 다시 입력해 주세요!");
      return;
    }

    alert('로그인 버튼 눌림');

    axios.post('https://localhost:8080/login', 
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

  const handleUsernameChange = (event) => {
    setId(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPw(event.target.value);
  };

  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="box">
                <div className = "login-title">
                  로그인
                </div>
                <TextField
                    id="outlined-basic"
                    label="아이디"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {id}
                    onChange={handleUsernameChange}
                    style={{ width: '300px' }} 
                  />
                  <TextField
                    id="outlined-basic"
                    label="비밀번호"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {pw}
                    onChange={handlePasswordChange}
                    style={{ width: '300px' }} 
                  />
                  <button className="login-button" onClick={handleClick}>
                    로그인
                  </button>
                </div>
          </header>
        </div>
    );
  }
  
  export default Login;

