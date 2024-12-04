import React, { useState, useEffect } from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './Plan.css';
import { useNavigate } from 'react-router-dom';


function TripList() {
  const navigate = useNavigate();
  const [userName, setUserName] = useState('');
  const [tripData, setTripData] = useState({});

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
                            className="schedule-container2"
                            onClick={() => navigate(`/TripList-${trip.plannum}`)} 
                             >
                          <p><strong>여행 지역 :</strong> {trip.tripRegion}</p>
                          <p><strong>여행 기간 :</strong> {trip.startDate} - {trip.endDate}</p>
                          <p><strong>총 일수 :</strong> {trip.tripTotalDate}일</p>
                          <p><strong> 공개 여부 :</strong> {trip.tripopen ? '비공개' : '공개'}</p>
                        </div>
                    )) 
                    ) : (
                      <p style={{ fontSize: '0.8em', color: '#000000' }}>여행 일정이 없습니다.</p>
                    )}
                </div>
            </div>zz
          </header>
        </div>
    );
  }
  
  export default TripList;

