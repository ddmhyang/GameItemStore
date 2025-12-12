import React from 'react';

function Admin({ allMembers, banMember }) {
  return (
    <div className="container fade-in">
      <div className="card admin-card">
        <h3>🛡️ 관리자 페이지</h3>
        <table className="admin-table">
          <thead><tr><th>ID</th><th>닉네임</th><th>마일리지</th><th>관리</th></tr></thead>
          <tbody>
            {allMembers.map(m => (
              <tr key={m.id}>
                <td>{m.loginId}</td>
                <td>{m.nickname}</td>
                <td>{m.mileage.toLocaleString()}</td>
                <td>
                  {m.role !== 'ADMIN' && <button onClick={() => banMember(m.id)} className="btn-ban">강제탈퇴</button>}
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