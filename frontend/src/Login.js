import React, { useState } from 'react';
import { loginApi } from './api'; // [NEW] 만든 api 불러오기
import './App.css';

function Login({ onLogin, onSwitch }) {
  const [form, setForm] = useState({ loginId: '', password: '' });

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = () => {
    // [수정] 복잡한 axios.post(...) 대신 loginApi(form) 한 줄이면 끝!
    loginApi(form)
      .then(response => {
        if (response.data) {
          alert(response.data.nickname + "님 환영합니다! 🎉");
          if (response.data.token) {
            localStorage.setItem("token", response.data.token);
          }
          onLogin(response.data);
        } else {
          alert("아이디/비번 확인해주세요.");
        }
      })
      .catch(() => alert("로그인 오류"));
  };

  const handleKeyDown = (e) => { if (e.key === 'Enter') handleSubmit(); };

  return (
    <div className="input-box">
      <h3>🔐 로그인</h3>
      <input name="loginId" placeholder="아이디" onChange={handleChange} onKeyDown={handleKeyDown} />
      <input name="password" type="password" placeholder="비밀번호" onChange={handleChange} onKeyDown={handleKeyDown} />
      <button onClick={handleSubmit}>로그인</button>
      <p style={{marginTop: '10px', fontSize: '0.9rem', color: '#ccc'}}>
        아직 회원이 아니신가요? <span onClick={onSwitch} style={{color: '#4cc9f0', cursor: 'pointer', fontWeight: 'bold'}}>회원가입</span>
      </p>
    </div>
  );
}
export default Login;