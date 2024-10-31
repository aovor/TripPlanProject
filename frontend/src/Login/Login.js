import React, {useState} from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './Login.css';

function Login() {

  const [userid, setUserId] = useState('')
  const [pw, setPw] = useState('')

  const handleClick = () => {
    if (!userid || !pw) {
      alert("아이디와 비밀번호를 다시 입력해 주세요!");
      return;
    }

    alert('로그인 버튼 눌림');

    axios.post('/api/auth/login', 
      {
        userId: userid,  
        password: pw    
      },
    )
    .then(response => {
      if (response.status == 200) {
        const token = response.data.token;
        localStorage.setItem('token', token); // 토큰 저장

        alert('로그인');
        console.log('로그인 성공:', response);
      }
      else if (response.status == 401){
        alert('아이디, 비밀번호가 틀렸습니다.')
        console.log('401 Unauthorized')
      }
      else if (response.status = 500){
        console.log('500 Internal Server Error, 서버오류')
      }
    })
    .catch(error => {
      console.error('로그인 실패:', error);
    });
  };

  const handleUsernameChange = (event) => {
    setUserId(event.target.value);
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
                    value = {userid}
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

                  <a href="/SearchPw" className="link" 
                     style={{ 
                              display: 'block', 
                              textAlign: 'right', 
                              width: '300px', 
                              marginBottom: '0.5rem',  
                              fontSize : '0.8rem'}}>

                    비밀번호 찾기
                  </a>

                  
                  <button className="login-button" onClick={handleClick} style={{ width: '300px' }}>
                    로그인
                  </button>

                  <div className="divider"></div>

                    <a href="/Signup" 
                      className="link" 
                      style = {{
                          fontSize : '0.8rem'
                        }}>
                        계정이 없다면? 회원가입
                    </a> 
                 

                </div>
          </header>
        </div>
    );
  }
  
  export default Login;

