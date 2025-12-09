package com.example.GameItemStore.repository;

import com.example.GameItemStore.entity.GameItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 스프링에게 "이건 DB 관리하는 애야"라고 알려줌
public interface GameItemRepository extends JpaRepository<GameItem, Long> {
    // 놀랍게도 여기에 코드를 한 줄도 안 써도 됩니다.
    // JpaRepository라는 엄청난 녀석을 상속받았기 때문에
    // 저장(save), 조회(findAll), 삭제(delete) 기능을 이미 다 가지고 있습니다.
}