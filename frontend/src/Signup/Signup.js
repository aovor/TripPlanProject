import React, {useState} from 'react';
import axios from 'axios'
import { TextField, Box } from '@mui/material';
import './Signup.css';
import { useNavigate } from 'react-router-dom';

function Signup() {

  const navigate = useNavigate();

  const [userid, setUserId] = useState('')
  const [isIdAvailable, setIsIdAvailable] = useState(null); // 아이디 사용 가능 여부 저장
  const [pw, setPw] = useState('')
  const [checkpw, setCheckPw] = useState('')
  const [email, setEmail] = useState('')
  const [isEmailAvailable, setIsEmailAvailable] = useState(null); // 이메일 사용 가능 여부 저장
  const [nickname, setNickName] = useState('')
  const [name, setName] = useState('')

  // 디바운스를 위한 타이머 상태
  const [emailCheckTimeout, setEmailCheckTimeout] = useState(null);

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // 이메일 유효성
  

  /* 회원가입 버튼 */
  const handleSignupClick = () => {
    // 비밀번호 유효성 검사: 대문자, 소문자, 숫자, 특수 문자 포함한 8자 이상
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]).{8,}$/;
    

    // 이메일 유효성 검사
    if (!emailRegex.test(email)) {
        alert('유효하지 않은 이메일 형식입니다. 올바른 이메일을 입력해주세요.');
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

    // 모든 항목 입력 검사 
    if(!name || !userid || !pw || !checkpw || !email) {
      alert('모든 항목을 입력해주세요');
      return;
    }
    
    // 이메일 중복 여부 확인
    if(isEmailAvailable === false ||isEmailAvailable === null ) {
      alert('중복되지 않는 이메일을 입력해주세요.')
      return;
    }

    // 아이디 중복 여부 확인
    if(isIdAvailable === false || isIdAvailable === null) {
      alert('아이디 중복확인을 해주세요.')
      return;
    }
    
    alert('회원가입 버튼 눌림');

    axios.post('/api/users/signup', 
      {
        userId: userid,  
        name: name,
        nickname: nickname,
        password: pw,
        email: email,
      }
    )
    .then(response => {
      if (response.status === 201) { 
        alert('회원가입 성공');
        console.log('회원가입 성공:', response);
        navigate('/ComSignup');
      } else {
        alert('회원가입 중 문제가 발생했습니다.');
      }
    })
    .catch(error => {
      if (error.response && error.response.status === 400) {
        alert('유효하지 않은 입력입니다. 항목을 다시 확인해주세요.');
      } else {
        console.error('회원가입 실패:', error);
        alert('서버 오류가 발생했습니다. 다시 시도해 주세요.');
      }
    });
};

  /* 아이디 중복 확인 */
  const checkIdAvailability = () => { 

    // 아이디 길이 검사(5글자 이상)
    if (userid.length < 5) {
      alert("아이디는 5글자 이상이어야 합니다.");
      setIsEmailAvailable(false);
      return;
    }

    axios.get('/api/users/check-id', { params: { userId: userid } })
      .then(response => {
        if (response.status === 200) {
          setIsIdAvailable(true);
          alert("사용 가능한 아이디입니다.");
        }
      })
      .catch(error => {
        setIsIdAvailable(false);
        alert("이미 사용 중이거나 유효하지 않은 아이디입니다.");
      });
  };

  /* 이메일 중복 확인 */
  const checkEmailAvailability = (email) => {
    
    // 이메일이 비어있거나 형식이 잘못된 경우 알림 없이 종료
    if (!email || !emailRegex.test(email)) {
      setIsEmailAvailable(null); // 알림을 숨기기 위해 초기화
      return;
    }

    axios.get('/api/users/check-email', { params: { email: email } })
    .then(response => {
      if (response.status === 200) {
        setIsEmailAvailable(true);  // 이메일 사용 가능
        alert("사용가능한 이메일입니다.")
      }
    })
    .catch(error => {
      if (error.response && error.response.status === 400) {
        setIsEmailAvailable(false);  // 이메일 이미 존재함
        alert("이메일이 이미 존재합니다.")
      } else {
        console.error("이메일 중복을 확인하는 중 에러가 발생했습니다.");
      }
    });
  }
  
  
  const handleUsernameChange = (event) => {
    setUserId(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPw(event.target.value);
  };

  const handleCheckpwChange = (event) => {
    setCheckPw(event.target.value);
  };

  const handleEmailChange = (event) => {
    const email = event.target.value;
    setEmail(email);

    // 이전 타이머가 있으면 클리어하여 디바운스 적용
    if (emailCheckTimeout) clearTimeout(emailCheckTimeout);

    // 일정 시간(예: 500ms) 동안 입력이 없으면 중복 확인 요청
    const newTimeout = setTimeout(() => {
      checkEmailAvailability(email);
    }, 500);
    setEmailCheckTimeout(newTimeout);
  };


  const handleNicknameChange = (event) => {
    setNickName(event.target.value);
  };

  const handleNameChange = (event) => {
    setName(event.target.value);
  };


  return (
        <div className="signup-Main">
          <header className="Main-header1">
              <div className="signup-box">
                <div className = "signup-title">
                  회원가입
                </div>
                <TextField
                      id="outlined-basic"
                      label="이름"
                      variant="outlined"
                      fullWidth
                      margin="normal"
                      value = {name}
                      onChange={handleNameChange}
                      style={{ width: '300px' }} 
                      inputProps={{
                        style: {
                            height: '15px'
                        }
                    }}
                    />

                  <TextField
                      id="outlined-basic"
                      label="닉네임"
                      variant="outlined"
                      fullWidth
                      margin="normal"
                      value = {nickname}
                      onChange={handleNicknameChange}
                      style={{ width: '300px' }} 
                      inputProps={{
                        style: {
                            height: '15px'
                        }
                    }}
                    />

                
                  <TextField
                      id="outlined-basic"
                      label="아이디"
                      variant="outlined"
                      fullWidth
                      margin="normal"
                      value = {userid}
                      onChange={handleUsernameChange}
                      style={{ width: '300px' }} 
                      inputProps={{
                        style: {
                            height: '15px'
                        }
                    }}
                    />
          
                  
                  <a className="link" 
                      onClick={(event) => {
                        checkIdAvailability();
                    }}
                     style={{ 
                              display: 'block', 
                              textAlign: 'right', 
                              width: '300px', 
                              marginBottom: '0.5rem',  
                              fontSize : '0.8rem',
                              cursor: 'pointer' // 마우스 포인터 추가
                              }}>

                    중복 확인
                  </a>

                  <TextField
                    id="outlined-basic"
                    label="비밀번호"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {pw}
                    type = "password"
                    onChange={handlePasswordChange}
                    style={{ width: '300px' }} 
                    inputProps={{
                      style: {
                          height: '15px'
                      }
                  }}
                  />
                  <TextField
                    id="outlined-basic"
                    label="비밀번호 확인"
                    variant="outlined"
                    fullWidth
                    margin="normal"
                    value = {checkpw}
                    type = "password"
                    onChange={handleCheckpwChange}
                    style={{ width: '300px' }} 
                    inputProps={{
                      style: {
                          height: '15px'
                      }
                  }}
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
                    inputProps={{
                      style: {
                          height: '15px'
                      }
                  }}
                  />
                  

                  <button className="login-button" onClick={handleSignupClick} style={{ width: '300px' }}>
                    회원가입
                  </button>
 
                </div>
          </header>
        </div>
    );
  }
  
  export default Signup;

