import React, {useState} from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './SearchPw.css';
import { useNavigate } from 'react-router-dom';

function ResetPw() {

  const [pw, setPw] = useState('')
  const [checkpw, setCheckPw] = useState('')

  const navigate = useNavigate();

  
  const gotoComPw = () => {
      navigate('/ComPw')
  }

  // 비밀번호 유효성 검사: 대문자, 소문자, 숫자, 특수 문자 포함한 8자 이상
  const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]).{8,}$/;

  const handleClick = () => {
    
    // 항목 검사
    if (!pw || !checkpw) {
      alert("비밀번호와 비밀번호 확인을 다시 입력해주세요!");
      return;
    }

    // 비밀번호 유효성 검사
    if (!passwordRegex.test(pw)) {
        alert('비밀번호는 대문자, 소문자, 숫자, 특수 문자를 포함한 8자 이상이어야 합니다.');
        return; 
    }

    // 비밀번호와 비밀번호 확인 일치 여부 검사
    if (pw !== checkpw) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
  }
    /* 비밀번호 재설정 API 추가 
    - 새로 설정한 비밀번호 보내주기
     */
    alert('비밀번호 재설정 버튼 눌림');

  };


  const handlePwChange = (event) => {
    setPw(event.target.value);
  };

  const handleCheckpwChange = (event) => {
    setCheckPw(event.target.value);
  };

  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="box">
                <div className = "login-title">
                  비밀번호 재설정
                </div>
                <TextField
                    id="outlined-basic"
                    label="비밀번호"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {pw}
                    onChange={handlePwChange}
                    style={{ width: '300px' }} 
                  />
                  <TextField
                    id="outlined-basic"
                    label="비밀번호 확인"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {checkpw}
                    onChange={handleCheckpwChange}
                    style={{ width: '300px' }} 
                  />

                  <button 
                          className="login-button" 
                          onClick={handleClick} 
                          style={{ width: '300px' }}
                  >
                    비밀번호 재설정
                  </button>

                </div>
          </header>
        </div>
    );
  }
  
  export default ResetPw;

