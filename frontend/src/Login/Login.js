import React from 'react';
import { TextField, Box } from '@mui/material';
import './Login.css';

function Login() {

  const handleClick = () => {
    alert('로그인');
  };

      return (
        <div className="Main">
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
                    style={{ width: '300px' }} 
                  />
                  <TextField
                    id="outlined-basic"
                    label="비밀번호"
                    variant="outlined"
                    fullWidth
                    margin="normal"
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

