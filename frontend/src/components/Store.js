import React, { useState } from 'react';

function Store({ items, user, form, handleItemChange, handleItemSubmit, handleBuy, deleteItem, onSearch }) {
  const [keyword, setKeyword] = useState('');    return (
    <div className="container fade-in">

      <div className="search-bar" style={{marginBottom: '20px', display:'flex', gap:'10px'}}>
        <input
          placeholder="🔍 아이템 검색 (예: 검, 포션)"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          onKeyDown={(e) => e.key === 'Enter' && onSearch(keyword)}
          style={{flex: 1, padding: '12px', borderRadius: '8px', border: '1px solid #ddd'}}
        />
        <button
          onClick={() => onSearch(keyword)}
          style={{background:'#4f46e5', color:'white', border:'none', padding:'0 20px', borderRadius:'8px', cursor:'pointer'}}
        >
          검색
        </button>
      </div>
      <div className="card item-form">
        <h3>📦 물품 등록</h3>
        <div className="form-row">
          <input name="itemName" value={form.itemName} placeholder="상품명" onChange={handleItemChange} />
          <input name="price" value={form.price} type="number" placeholder="가격" onChange={handleItemChange} />
          <input name="description" value={form.description} placeholder="설명" onChange={handleItemChange} className="desc-input"/>
          <button onClick={handleItemSubmit} className="btn-primary">등록</button>
        </div>
      </div>

      <div className="grid-container">
        {items.map(item => (
          <div key={item.id} className={`card item-card ${item.sold ? 'sold' : ''}`}>
            <div className="card-header">
              <h4>{item.itemName}</h4>
              {item.sold && <span className="badge-sold">판매완료</span>}
            </div>
            <p className="desc">{item.description}</p>
            <div className="price-tag">{item.price.toLocaleString()} 원</div>
            <div className="seller-info">판매자: {item.seller?.nickname}</div>

            {!item.sold && item.seller?.id !== user.id && (
              <button onClick={() => handleBuy(item.id)} className="btn-buy">구매하기</button>
            )}
            {user.role === 'ADMIN' && (
              <button onClick={() => deleteItem(item.id)} className="btn-delete">삭제 (관리자)</button>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Store;