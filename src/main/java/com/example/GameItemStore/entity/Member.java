package com.example.GameItemStore.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 번호

    @Column(unique = true) // 아이디 중복 방지
    private String loginId; // 로그인 아이디 (예: user1)

    private String password; // 비밀번호

    private String nickname; // 닉네임 (예: 겜돌이)

    // [기획서 반영] 마일리지: 0원부터 시작
    private int mileage = 0;

    // [기획서 반영] 권한: "USER"(일반), "ADMIN"(관리자)
    private String role;

    @Transient
    private String token;
}