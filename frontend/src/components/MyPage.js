import React from 'react';

function MyPage({ user, myItems, myPageTab, setMyPageTab, handleWithdraw }) {
  return (
    <div className="container fade-in">
      <div className="mypage-layout">
        <div className="card wallet-card">
          <h3>💰 내 지갑</h3>
          <div className="balance">{user.mileage.toLocaleString()} P</div>
          <button onClick={handleWithdraw} className="btn-withdraw">출금하기</button>
        </div>

        <div className="card history-card">
          <div className="tabs">
            <button onClick={() => setMyPageTab('selling')} className={myPageTab === 'selling' ? 'active' : ''}>판매 내역</button>
            <button onClick={() => setMyPageTab('buying')} className={myPageTab === 'buying' ? 'active' : ''}>구매 내역</button>
          </div>
          <ul className="history-list">
            {myItems.map(item => (
              <li key={item.id}>
                <div className="history-info">
                  <span className="name">{item.itemName}</span>
                  <span className="price">{item.price.toLocaleString()} 원</span>
                </div>
                <div className="status">
                  {myPageTab === 'selling' ? (
                    item.sold ? <span className="success">판매완료 ({item.buyer?.nickname})</span> : <span className="waiting">판매중</span>
                  ) : (
                    <span className="success">구매완료 (판매자: {item.seller?.nickname})</span>
                  )}
                </div>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}

export default MyPage;