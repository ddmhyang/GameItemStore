package com.example.GameItemStore.service;

import com.example.GameItemStore.entity.GameItem;
import com.example.GameItemStore.entity.Member; // Member 가져오기
import com.example.GameItemStore.repository.GameItemRepository;
import com.example.GameItemStore.repository.MemberRepository; // Member DB 도구 가져오기
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameItemService {

    @Autowired
    private GameItemRepository gameItemRepository;

    @Autowired // [추가] 회원 정보도 찾아야 하니까 이것도 필요함
    private MemberRepository memberRepository;

    // [수정] 아이템 저장할 때 '누가(sellerId)' 파는지도 같이 받음
    public void write(GameItem gameItem, Long sellerId) {
        // 1. 판매자 ID로 회원 정보를 찾는다. (없으면 null)
        Member seller = memberRepository.findById(sellerId).orElse(null);

        // 2. 아이템에 판매자 정보를 넣어준다.
        gameItem.setSeller(seller);

        // 3. 저장!
        gameItemRepository.save(gameItem);
    }

    public List<GameItem> gameItemList() {
        return gameItemRepository.findAll();
    }
    public void buy(Long itemId, Long buyerId) {
        // 1. 아이템과 구매자 정보를 가져옴
        GameItem item = gameItemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("아이템 없음"));
        Member buyer = memberRepository.findById(buyerId).orElseThrow(() -> new IllegalArgumentException("구매자 없음"));

        // 2. 유효성 검사 (이미 팔린 건지? 돈은 있는지?)
        if (item.isSold()) {
            throw new IllegalStateException("이미 팔린 아이템입니다.");
        }
        if (buyer.getMileage() < item.getPrice()) {
            throw new IllegalStateException("마일리지가 부족합니다! 충전하세요.");
        }
        if (item.getSeller().getId().equals(buyer.getId())) {
            throw new IllegalStateException("본인 물건은 살 수 없습니다.");
        }

        // 3. 돈 계산 (수수료 5%)
        int price = item.getPrice();
        int commission = (int) (price * 0.05); // 5% 수수료
        int sellerMoney = price - commission;  // 판매자가 받을 돈

        // 4. 마일리지 이동
        // 구매자: 돈 차감
        buyer.setMileage(buyer.getMileage() - price);

        // 판매자: 돈 입금 (판매자가 존재할 때만)
        if (item.getSeller() != null) {
            Member seller = item.getSeller();
            seller.setMileage(seller.getMileage() + sellerMoney);
            memberRepository.save(seller); // 판매자 지갑 업데이트
        }

        // 5. 아이템 상태 변경 (판매 완료 처리)
        item.setSold(true);

        // 6. 저장 (구매자 지갑 + 아이템 상태 업데이트)
        memberRepository.save(buyer);
        gameItemRepository.save(item);
    }
    public void buy(Long itemId, Long buyerId) {
        GameItem item = gameItemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("아이템 없음"));
        Member buyer = memberRepository.findById(buyerId).orElseThrow(() -> new IllegalArgumentException("구매자 없음"));

        if (item.isSold()) throw new IllegalStateException("이미 팔림");
        if (buyer.getMileage() < item.getPrice()) throw new IllegalStateException("돈 부족");
        if (item.getSeller().getId().equals(buyer.getId())) throw new IllegalStateException("본인 물건 구매 불가");

        // 돈 계산
        int price = item.getPrice();
        int commission = (int) (price * 0.05);
        Member seller = item.getSeller();

        // 마일리지 이동
        buyer.setMileage(buyer.getMileage() - price);
        if (seller != null) {
            seller.setMileage(seller.getMileage() + (price - commission));
            memberRepository.save(seller);
        }

        // [중요] 구매자 도장 쾅! & 판매 완료 처리
        item.setBuyer(buyer); // <--- 여기 추가됨!
        item.setSold(true);

        memberRepository.save(buyer);
        gameItemRepository.save(item);
    }

    // [추가] 판매 내역 가져오기
    public List<GameItem> getMySellingItems(Long memberId) {
        return gameItemRepository.findAllBySellerId(memberId);
    }

    // [추가] 구매 내역 가져오기
    public List<GameItem> getMyBuyingItems(Long memberId) {
        return gameItemRepository.findAllByBuyerId(memberId);
    }
}