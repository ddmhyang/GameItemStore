import React, { useState } from 'react';
import { joinApi } from './api';
import './App.css';

function Signup({ onSwitch }) {
  const [form, setForm] = useState({ loginId: '', password: '', nickname: '' });

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = () => {
    if (!form.loginId || !form.password || !form.nickname) return alert("빈칸을 채워주세요.");

    joinApi(form)
      .then(response => {
        alert(response.data);
        onSwitch();
      })
      .catch(() => alert("회원가입 실패 (ID 중복 등)"));
  };

  return (
    <div className="input-box">
      <h3>👋 회원가입</h3>
      <input name="loginId" placeholder="아이디" onChange={handleChange} />
      <input name="password" type="password" placeholder="비밀번호" onChange={handleChange} />
      <input name="nickname" placeholder="닉네임" onChange={handleChange} />
      <button onClick={handleSubmit}>가입하기</button>
      <p style={{marginTop: '10px', fontSize: '0.9rem', color: '#ccc'}}>
        이미 계정이 있나요? <span onClick={onSwitch} style={{color: '#4cc9f0', cursor: 'pointer', fontWeight: 'bold'}}>로그인하러 가기</span>
      </p>
    </div>
  );
}

export default Signup;