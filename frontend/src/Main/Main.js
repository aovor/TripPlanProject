import React from 'react';
import './Main.css';
import { useNavigate } from "react-router-dom";
import axios from "axios";


function Main() {
  const navigate = useNavigate();

    const handleClick = () => {
      navigate('/Login');
    };

    const handleClick2 = () => {
      alert('회원가입');
  };

    return (
      <div className="Main">
        <header className="Main-header">
          {/* 왼쪽 그리드 */}
          <div className="left-grid">
            <div className="Title" >
              Trip Plan
              <div className="Content" >
                손쉬운 여행 계획 짜기
              </div>
            </div> 
            <button className="custom-button" onClick={handleClick}>
              로그인
            </button>
            <button className="custom-button" onClick={handleClick2}>
              회원가입
            </button>
          </div>

        {/* 오른쪽 그리드 */}
          <div className="right-grid">
            <div>ㅡㅡ</div> {/* 여기 오른쪽 그리드에 "안녕" 추가 */}
          </div>
        </header>
      </div>
    );
}

export default Main;
