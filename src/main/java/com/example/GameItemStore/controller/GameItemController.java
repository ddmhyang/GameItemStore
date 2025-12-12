package com.example.GameItemStore.controller;

import com.example.GameItemStore.entity.GameItem;
import com.example.GameItemStore.service.GameItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.GameItemStore.dto.GameItemDto;
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

    @PostMapping("/api/items")
    public String addItem(@RequestBody GameItem gameItem, @RequestParam Long sellerId) {
        gameItemService.write(gameItem, sellerId);
        return "저장 성공!";
    }

    @PostMapping("/api/items/{itemId}/buy")
    public String buyItem(@PathVariable Long itemId, @RequestParam Long buyerId) {
        gameItemService.buy(itemId, buyerId);
        return "구매 성공!";
    }

    @GetMapping("/api/items")
    public List<GameItem> getItems(@RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return gameItemService.searchItems(keyword);
        }
        return gameItemService.gameItemList();
    }

    @GetMapping("/api/items/my-buying")
    public List<GameItem> getMyBuyingItems(@RequestParam Long memberId) {
        return gameItemService.getMyBuyingItems(memberId);
    }

    @Autowired
    private com.example.GameItemStore.repository.GameItemRepository gameItemRepository;

    @DeleteMapping("/api/items/{id}")
    public String deleteItem(@PathVariable Long id) {
        gameItemRepository.deleteById(id);
        return "아이템 삭제 완료";
    }

    @PostMapping("/api/items")
    public String addItem(@RequestBody GameItemDto itemDto, @RequestParam Long sellerId) {
        GameItem gameItem = new GameItem();
        gameItem.setItemName(itemDto.getItemName());
        gameItem.setPrice(itemDto.getPrice());
        gameItem.setDescription(itemDto.getDescription());

        gameItemService.write(gameItem, sellerId);
        return "저장 성공!";
    }
}