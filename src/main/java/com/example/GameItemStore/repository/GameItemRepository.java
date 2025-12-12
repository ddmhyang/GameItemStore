package com.example.GameItemStore.repository;

import com.example.GameItemStore.entity.GameItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    List<GameItem> findAllBySellerId(Long sellerId);
    List<GameItem> findAllByBuyerId(Long buyerId);
    List<GameItem> findByItemNameContaining(String keyword);
}