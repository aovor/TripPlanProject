import React from 'react';
import './Main.css';

function Main() {
    const handleClick = () => {
        alert('로그인');
    };

    const handleClick2 = () => {
      alert('회원가입');
  };

    return (
      <div className="Main">
        <header className="Main-header">
          <button className="custom-button" onClick={handleClick}>
            로그인
          </button>
          <button className="custom-button" onClick={handleClick2}>
            회원가입
          </button>
        </header>
      </div>
    );
}

export default Main;
