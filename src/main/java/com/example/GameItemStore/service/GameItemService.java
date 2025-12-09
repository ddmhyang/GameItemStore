package com.example.GameItemStore.service;

import com.example.GameItemStore.entity.GameItem;
import com.example.GameItemStore.repository.GameItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 스프링에게 "이건 핵심 업무 처리하는 애야"라고 알려줌
public class GameItemService {

    @Autowired // [중요] 스프링에게 "아까 만든 Repository(DB 담당자) 좀 데려와줘"라고 부탁함
    private GameItemRepository gameItemRepository;

    // 기능 1: 아이템 저장하기
    public void write(GameItem gameItem) {
        // DB 담당자(repository)에게 저장을 시킴
        // 이때 실제 SQL문: INSERT INTO game_item VALUES (...) 가 실행됨
        gameItemRepository.save(gameItem);
    }

    // 기능 2: 아이템 목록 모두 가져오기
    public List<GameItem> gameItemList() {
        // DB 담당자에게 모든 데이터를 가져오라고 시킴
        // 이때 실제 SQL문: SELECT * FROM game_item 이 실행됨
        return gameItemRepository.findAll();
    }
}