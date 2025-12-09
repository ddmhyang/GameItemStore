package com.example.GameItemStore.service;

import com.example.GameItemStore.entity.GameItem;
import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.repository.GameItemRepository;
import com.example.GameItemStore.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 거래 안전장치 추가

import java.util.List;

@Service
@Transactional // [추가] 데이터 저장/수정 중 오류나면 취소해주는 안전장치
public class GameItemService {

    @Autowired
    private GameItemRepository gameItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 1. 아이템 저장 (판매자 등록 포함)
    public void write(GameItem gameItem, Long sellerId) {
        Member seller = memberRepository.findById(sellerId).orElse(null);
        gameItem.setSeller(seller);
        gameItemRepository.save(gameItem);
    }

    // 2. 전체 아이템 목록 가져오기
    public List<GameItem> gameItemList() {
        return gameItemRepository.findAll();
    }

    // 3. [핵심] 아이템 구매 기능 (하나만 있어야 함!)
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

        // 돈 계산 (수수료 5%)
        int price = item.getPrice();
        int commission = (int) (price * 0.05);
        Member seller = item.getSeller();

        // 구매자 돈 차감
        buyer.setMileage(buyer.getMileage() - price);

        // 판매자 돈 입금
        if (seller != null) {
            seller.setMileage(seller.getMileage() + (price - commission));
            memberRepository.save(seller);
        }

        // 아이템 상태 변경 (구매자 기록 + 품절 처리)
        item.setBuyer(buyer);
        item.setSold(true);

        // 최종 저장
        memberRepository.save(buyer);
        gameItemRepository.save(item);
    }

    // 4. 내 판매 내역 가져오기
    public List<GameItem> getMySellingItems(Long memberId) {
        return gameItemRepository.findAllBySellerId(memberId);
    }

    // 5. 내 구매 내역 가져오기
    public List<GameItem> getMyBuyingItems(Long memberId) {
        return gameItemRepository.findAllByBuyerId(memberId);
    }
}