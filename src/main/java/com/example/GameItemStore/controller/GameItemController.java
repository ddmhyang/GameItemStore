package com.example.GameItemStore.controller;

import com.example.GameItemStore.entity.GameItem; // 회원님 패키지명(entiy) 유지
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

    @PostMapping("/api/items")
    public String addItem(@RequestBody GameItem gameItem) {
        gameItemService.write(gameItem);
        return "저장 성공!";
    }
}