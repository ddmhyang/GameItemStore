package com.example.GameItemStore.controller;

import com.example.GameItemStore.dto.GameItemDto;
import com.example.GameItemStore.entity.GameItem;
import com.example.GameItemStore.service.GameItemService;
import com.example.GameItemStore.repository.GameItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameItemController {

    @Autowired
    private GameItemService gameItemService;

    @Autowired
    private GameItemRepository gameItemRepository;

    @GetMapping("/api/items")
    public List<GameItem> getItems(@RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return gameItemService.searchItems(keyword);
        }
        return gameItemService.gameItemList();
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

    @PostMapping("/api/items/{itemId}/buy")
    public String buyItem(@PathVariable Long itemId, @RequestParam Long buyerId) {
        gameItemService.buy(itemId, buyerId);
        return "구매 성공!";
    }

    @GetMapping("/api/items/my-selling")
    public List<GameItem> getMySellingItems(@RequestParam Long memberId) {
        return gameItemService.getMySellingItems(memberId);
    }

    @GetMapping("/api/items/my-buying")
    public List<GameItem> getMyBuyingItems(@RequestParam Long memberId) {
        return gameItemService.getMyBuyingItems(memberId);
    }

    @DeleteMapping("/api/items/{id}")
    public String deleteItem(@PathVariable Long id) {
        gameItemRepository.deleteById(id);
        return "아이템 삭제 완료";
    }

    @PutMapping("/api/items/{id}")
    public String updateItem(@PathVariable Long id, @RequestBody GameItemDto dto) {
        gameItemService.updateItem(id, dto.getItemName(), dto.getPrice(), dto.getDescription());
        return "아이템 수정 완료";
    }}