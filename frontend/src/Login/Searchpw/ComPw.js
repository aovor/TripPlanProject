import React from 'react';
import axios from 'axios'
import { useNavigate } from "react-router-dom";

function ComPw() {

    const navigate = useNavigate();

    const handleClick = () => {
      navigate('/Login');
    };

  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="box">
                <div className = "login-title">
                  비밀번호 재설정 완료
                </div>               
                  <button 
                        className="login-button" 
                        onClick={handleClick} 
                        style={{ width: '200px' }}
                    >
                    로그인
                  </button>

                </div>
          </header>
        </div>
    );
  }
  
  export default ComPw;

