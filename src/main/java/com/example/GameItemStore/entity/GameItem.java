package com.example.GameItemStore.entity;

import jakarta.persistence.*; // JPA 관련 도구들을 다 가져옴
import lombok.Data; // 게터, 세터 자동 생성 도구

@Entity // [중요] 스프링에게 "이건 DB 테이블이야!"라고 알려주는 표시
@Data   // Lombok 기능: getTitle(), setTitle() 같은 코드를 자동으로 만들어줌
public class GameItem {

    @Id // [중요] "이게 주민등록번호(Primary Key)야!"라고 알려줌
    @GeneratedValue(strategy = GenerationType.IDENTITY) // "번호는 네가 알아서 1씩 증가시켜(Auto Increment)"
    private Long id; // 아이템 고유 번호 (1번, 2번, 3번...)

    private String itemName; // 아이템 이름 (예: "전설의 검")

    private int price; // 가격 (예: 1000)

    private String description; // 설명 (예: "공격력이 100 증가합니다.")
}