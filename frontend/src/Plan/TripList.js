import React, { useState, useEffect } from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './Plan.css';
import { useNavigate } from 'react-router-dom';
import trip1 from '../img/trip1.png';
import trip2 from '../img/trip3.jpg';


function TripList() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState('');
  const [tripData, setTripData] = useState({});

  const images = [trip1, trip2];  // src 폴더 경로

  useEffect(() => {
    // 로컬 스토리지에서 토큰을 가져옴
    const token = localStorage.getItem('token');
    
    /* - 테스트 끝나고 넣기
    if (!token) {
      alert('로그인이 필요합니다.');
      navigate('/login'); // 로그인 페이지로 이동
      return;
    }
    */

    // 사용자 정보 가져오기
    axios.get('/api/users/userdetails', {
      headers: {
        Authorization: `Bearer ${token}`, 
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (response.status === 200) {
        setUserName(response.data.name); // 사용자 이름 상태에 저장
      }
    })
    .catch(error => {
      console.error('사용자 정보 가져오기 실패:', error);
      // alert('사용자 정보를 가져오는데 실패했습니다.');
    });
    
    // 여행 목록 가져오기
    axios.get('/api/plan/planlist', {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json'  
        }
      })
      .then(response => {
        if (response.status === 200) {
            setTripData(response.data); // 여행 목록 상태에 저장
        }
      })
      .catch(error => {
        console.error('여행 목록 가져오기 실패:', error);
        alert('여행 목록을 가져오는 데 실패했습니다.');
      });
    }, [navigate]);


  
  return (
        <div className="Main2">
          <header className="Main-header1">
              <div className="plan-box">
                <div className="Plan-title">
                
                  <span style={{ color: '#43A047' }}>{userName || '00'}</span> 님의 여행
                </div>
                
                <div className="schedule-container">
                    {tripData.length > 0 ? (
                        tripData.map((trip, index) => (
                        <div 
                            key={index} 
                             >
                           <img 
                            className="plan-img" 
                            src={images[index % images.length]} 
                            alt="Trip Image" 
                            onClick={() => navigate(`/TripList-${trip.plannum}`)} 
                        />
                          <div className = "plan-region"> {trip.tripRegion} </div>
                          <div className="plan-date">  {trip.startDate} - {trip.endDate} ({trip.tripTotalDate}일) </div>
                       
                        </div>
                        
                    )) 
                    ) : (
                      <p style={{ fontSize: '0.8em', color: '#000000' }}>여행 일정이 없습니다.</p>
                    )}
                </div>
            </div>
          </header>
        </div>
    );
  }
  
  export default TripList;

