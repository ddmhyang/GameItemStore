import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function Login({ onLogin, onSwitch }) {
  const [form, setForm] = useState({ loginId: '', password: '' });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

// ... import 문 생략 ...

  const handleSubmit = () => {
    axios.post('http://localhost:8080/api/members/login', form)
      .then(response => {
        if (response.data) {
          alert(response.data.nickname + "님 환영합니다! 🎉");

          // [핵심] 받은 토큰을 브라우저에 저장! (교수님이 원하던 것)
          if (response.data.token) {
            localStorage.setItem("token", response.data.token);
            console.log("발급된 토큰:", response.data.token); // 개발자 도구 확인용
          }

          onLogin(response.data);
        } else {
          alert("아이디 또는 비밀번호가 틀렸습니다.");
        }
      })
      .catch(() => alert("로그인 오류 발생!"));
  };

// ... 나머지 UI 코드는 그대로 ...

  // [추가] 엔터키 감지 함수
  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      handleSubmit(); // 엔터 누르면 로그인 버튼 클릭한 것과 똑같이 실행
    }
  };

  return (
    <div className="input-box">
      <h3>🔐 로그인</h3>
      {/* [수정] 입력창에 onKeyDown={handleKeyDown} 추가 */}
      <input
        name="loginId"
        placeholder="아이디"
        onChange={handleChange}
        onKeyDown={handleKeyDown}
      />
      <input
        name="password"
        type="password"
        placeholder="비밀번호"
        onChange={handleChange}
        onKeyDown={handleKeyDown}
      />
      <button onClick={handleSubmit}>로그인</button>
      <p style={{marginTop: '10px', fontSize: '0.9rem', color: '#ccc'}}>
        아직 회원이 아니신가요? <span onClick={onSwitch} style={{color: '#4cc9f0', cursor: 'pointer', fontWeight: 'bold'}}>회원가입</span>
      </p>
    </div>
  );
}

export default Login;