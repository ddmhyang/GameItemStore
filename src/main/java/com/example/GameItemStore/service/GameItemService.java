package com.example.GameItemStore.service;

import com.example.GameItemStore.entity.GameItem;
import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.repository.GameItemRepository;
import com.example.GameItemStore.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameItemService {

    @Autowired
    private GameItemRepository gameItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    public void write(GameItem gameItem, Long sellerId) {
        Member seller = memberRepository.findById(sellerId).orElse(null);
        gameItem.setSeller(seller);
        gameItemRepository.save(gameItem);
    }

    public List<GameItem> gameItemList() {
        return gameItemRepository.findAll();
    }

    public void buy(Long itemId, Long buyerId) {
        GameItem item = gameItemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("아이템 없음"));
        Member buyer = memberRepository.findById(buyerId).orElseThrow(() -> new IllegalArgumentException("구매자 없음"));

        if (item.isSold()) {
            throw new IllegalStateException("이미 팔린 아이템입니다.");
        }
        if (buyer.getMileage() < item.getPrice()) {
            throw new IllegalStateException("마일리지가 부족합니다!");
        }
        if (item.getSeller() != null && item.getSeller().getId().equals(buyer.getId())) {
            throw new IllegalStateException("본인 물건은 살 수 없습니다.");
        }

        int price = item.getPrice();
        int commission = (int) (price * 0.05);
        Member seller = item.getSeller();

        buyer.setMileage(buyer.getMileage() - price);

        if (seller != null) {
            seller.setMileage(seller.getMileage() + (price - commission));
            memberRepository.save(seller);
        }

        item.setBuyer(buyer);
        item.setSold(true);

        memberRepository.save(buyer);
        gameItemRepository.save(item);
    }

    public List<GameItem> getMySellingItems(Long memberId) {
        return gameItemRepository.findAllBySellerId(memberId);
    }

    public List<GameItem> getMyBuyingItems(Long memberId) {
        return gameItemRepository.findAllByBuyerId(memberId);
    }

    public List<GameItem> searchItems(String keyword) {
        return gameItemRepository.findByItemNameContaining(keyword);
    }
}