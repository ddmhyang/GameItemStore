import React, { useState } from 'react';
import axios from 'axios';
import './App.css'; // 디자인 같이 쓰기

function Signup({ onSwitch }) {
  const [form, setForm] = useState({ loginId: '', password: '', nickname: '' });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    if (!form.loginId || !form.password || !form.nickname) {
      return alert("모든 칸을 채워주세요!");
    }

    axios.post('http://localhost:8080/api/members/join', form)
      .then(response => {
        alert(response.data); // "회원가입 성공!" 메시지 뜸
        onSwitch(); // 로그인 화면으로 전환
      })
      .catch(error => {
        console.error(error);
        alert("회원가입 실패! (아이디 중복 등)");
      });
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