import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function Login({ onLogin, onSwitch }) {
  const [form, setForm] = useState({ loginId: '', password: '' });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    axios.post('http://localhost:8080/api/members/login', form)
      .then(response => {
        if (response.data) {
          alert(response.data.nickname + "님 환영합니다! 🎉");
          onLogin(response.data); // 로그인 성공한 회원 정보 저장
        } else {
          alert("아이디 또는 비밀번호가 틀렸습니다.");
        }
      })
      .catch(() => alert("로그인 오류 발생!"));
  };

  return (
    <div className="input-box">
      <h3>🔐 로그인</h3>
      <input name="loginId" placeholder="아이디" onChange={handleChange} />
      <input name="password" type="password" placeholder="비밀번호" onChange={handleChange} />
      <button onClick={handleSubmit}>로그인</button>
      <p style={{marginTop: '10px', fontSize: '0.9rem', color: '#ccc'}}>
        아직 회원이 아니신가요? <span onClick={onSwitch} style={{color: '#4cc9f0', cursor: 'pointer', fontWeight: 'bold'}}>회원가입</span>
      </p>
    </div>
  );
}

export default Login;