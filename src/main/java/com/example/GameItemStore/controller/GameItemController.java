package com.example.GameItemStore.controller;

import com.example.GameItemStore.entity.GameItem;
import com.example.GameItemStore.service.GameItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameItemController {

    @Autowired
    private GameItemService gameItemService;

    @GetMapping("/api/items")
    public List<GameItem> getItems() {
        return gameItemService.gameItemList();
    }

    // [수정] 주소 뒤에 '?sellerId=1' 처럼 붙여서 보낼 것임 (@RequestParam 사용)
    @PostMapping("/api/items")
    public String addItem(@RequestBody GameItem gameItem, @RequestParam Long sellerId) {
        gameItemService.write(gameItem, sellerId);
        return "저장 성공!";
    }

    // [구매 요청] POST /api/items/{id}/buy?buyerId=구매자ID
    @PostMapping("/api/items/{itemId}/buy")
    public String buyItem(@PathVariable Long itemId, @RequestParam Long buyerId) {
        try {
            gameItemService.buy(itemId, buyerId);
            return "구매 성공!";
        } catch (Exception e) {
            // 돈이 없거나 이미 팔렸으면 에러 메시지를 보냄
            return "구매 실패: " + e.getMessage();
        }
    }
    @GetMapping("/api/items/my-selling")
    public List<GameItem> getMySellingItems(@RequestParam Long memberId) {
        return gameItemService.getMySellingItems(memberId);
    }

    // 2. 내 구매 내역 (GET /api/items/my-buying?memberId=1)
    @GetMapping("/api/items/my-buying")
    public List<GameItem> getMyBuyingItems(@RequestParam Long memberId) {
        return gameItemService.getMyBuyingItems(memberId);
    }

    @Autowired
    private com.example.GameItemStore.repository.GameItemRepository gameItemRepository;

    // [관리자용] 아이템 강제 삭제
    @DeleteMapping("/api/items/{id}")
    public String deleteItem(@PathVariable Long id) {
        gameItemRepository.deleteById(id);
        return "아이템 삭제 완료";
    }
}