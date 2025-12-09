package com.example.GameItemStore.repository;

import com.example.GameItemStore.entity.GameItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    // 1. 내가 판매자인 아이템들 찾기 (판매 내역)
    List<GameItem> findAllBySellerId(Long sellerId);

    // 2. 내가 구매자인 아이템들 찾기 (구매 내역)
    List<GameItem> findAllByBuyerId(Long buyerId);

    // 3. (나중에 검색 기능용) 이름으로 찾기
    List<GameItem> findByItemNameContaining(String keyword);
}