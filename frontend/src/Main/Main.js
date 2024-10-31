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
      navigate('/Signup');
  };

  // 다음 섹션으로 스크롤
  const scrollToSection = (section) => {
    const element = document.getElementById(section);
    if (element) {
      element.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });
    }
  };

    return (
      <div className="Main">
        <header section id="Main-header" className="Main-header">
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
            <div className="circle-navigation">
              <div className="circle" onClick={() => scrollToSection('Main-header')}></div>
              <div className="circle" onClick={() => scrollToSection('additional-content')}></div>
              <div className="circle" onClick={() => scrollToSection('additional-content2')}></div>
              <div className="circle" onClick={() => scrollToSection('additional-content3')}></div>
            </div>
        </header>

        

        {/* 추가 설명 섹션 */}
        <section id="additional-content" className="additional-content">
            <h2>Trip Plan #1</h2>
            <p>효율적인 여행 일정</p>
          </section>

          <section id="additional-content2" className="additional-content2">
            <h2>Trip Plan #2</h2>
            <p>여행 파트너와 일정 공유</p>
          </section>

          <section id="additional-content3" className="additional-content3">
            <h2>Trip Plan #3</h2>
            <p>활발한 커뮤니티</p>
          </section>


      </div>
    );
}

export default Main;
