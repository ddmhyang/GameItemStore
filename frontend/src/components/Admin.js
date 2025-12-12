import React from 'react';

function Admin({ allMembers, banMember, updateMileage, allItems, deleteItem, updateItem }) {
  return (
    <div className="container fade-in">

      {/* 1. 회원 관리 섹션 */}
      <div className="card admin-card" style={{marginBottom: '30px'}}>
        <h3>👥 회원 관리</h3>
        <table className="admin-table">
          <thead><tr><th>ID</th><th>닉네임</th><th>마일리지</th><th>관리</th></tr></thead>
          <tbody>
            {allMembers.map(m => (
              <tr key={m.id}>
                <td>{m.loginId}</td>
                <td>{m.nickname}</td>
                <td>
                  {m.mileage.toLocaleString()} P
                  <button onClick={() => updateMileage(m.id, m.mileage)} style={{marginLeft:'10px', fontSize:'0.8rem', cursor:'pointer'}}>✏️</button>
                </td>
                <td>
                  {m.role !== 'ADMIN' && <button onClick={() => banMember(m.id)} className="btn-ban">추방</button>}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* 2. 아이템 관리 섹션 (새로 추가됨) */}
      <div className="card admin-card">
        <h3>📦 전체 아이템 관리</h3>
        <table className="admin-table">
          <thead><tr><th>상품명</th><th>가격</th><th>상태</th><th>판매자</th><th>관리</th></tr></thead>
          <tbody>
            {allItems.map(item => (
              <tr key={item.id}>
                <td>{item.itemName}</td>
                <td>{item.price.toLocaleString()}</td>
                <td>{item.sold ? <span style={{color:'red'}}>판매완료</span> : <span style={{color:'green'}}>판매중</span>}</td>
                <td>{item.seller ? item.seller.nickname : '(탈퇴)'}</td>
                <td>
                  <button onClick={() => updateItem(item)} style={{marginRight:'5px', cursor:'pointer'}}>✏️수정</button>
                  <button onClick={() => deleteItem(item.id)} className="btn-ban">🗑️삭제</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

    </div>
  );
}

export default Admin;