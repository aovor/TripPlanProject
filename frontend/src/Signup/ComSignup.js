import React from 'react';
import axios from 'axios'
import { useNavigate } from "react-router-dom";

function ComSignup() {

    const navigate = useNavigate();

    const handleClick = () => {
      navigate('/');
    };

  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="box">
                <div className = "login-title">
                  회원가입 완료
                </div>               
                  <button 
                        className="login-button" 
                        onClick={handleClick} 
                        style={{ width: '200px' }}
                    >
                    메인화면으로
                  </button>

                </div>
          </header>
        </div>
    );
  }
  
  export default ComSignup;

