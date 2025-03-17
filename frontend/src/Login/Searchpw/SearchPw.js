import React, {useState} from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './SearchPw.css';
import { useNavigate } from 'react-router-dom';

function SearchPw() {

  const [userid, setUserId] = useState('')
  const [email, setEmail] = useState('')

  const navigate = useNavigate();

  const gotoReset = () => {
      navigate('/ResetPw')
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 이메일 유효성

  const handleClick = () => {
    if (!userid || !email) {
      alert("아이디와 이메일을 다시 입력해 주세요!");
      return;
    }

    // 이메일 유효성 검사
    if (!email || !emailRegex.test(email)) {
      alert('유효하지 않은 이메일 형식입니다. 올바른 이메일을 입력해주세요.');
      return; 
  }

    alert('회원가입 버튼 눌림');

  };


  const handleUsernameChange = (event) => {
    setUserId(event.target.value);
  };

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };

  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="box">
                <div className = "login-title">
                  비밀번호 찾기
                </div>
                <TextField
                    id="outlined-basic"
                    label="아이디"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {userid}
                    onChange={handleUsernameChange}
                    style={{ width: '300px' }} 
                  />
                  <TextField
                    id="outlined-basic"
                    label="이메일"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {email}
                    onChange={handleEmailChange}
                    style={{ width: '300px' }} 
                  />

                  <button 
                          className="login-button" 
                          onClick={handleClick} 
                          style={{ width: '300px' }}
                  >
                    비밀번호 찾기
                  </button>

                </div>
          </header>
        </div>
    );
  }
  
  export default SearchPw;

