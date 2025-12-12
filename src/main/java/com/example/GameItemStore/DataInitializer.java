package com.example.GameItemStore;

import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        // 1. "admin"이라는 아이디가 이미 있는지 확인
        if (memberRepository.findByLoginId("admin").isEmpty()) {

            // 2. 없으면 관리자 계정 생성
            Member admin = new Member();
            admin.setLoginId("admin");
            admin.setPassword("1234");
            admin.setNickname("총관리자");
            admin.setRole("ADMIN");     // 핵심: 관리자 권한
            admin.setMileage(1000000);  // 테스트 편하게 돈도 두둑이 줌 (100만)

            memberRepository.save(admin);

            System.out.println("======================================");
            System.out.println(" [알림] 관리자 계정이 생성되었습니다!");
            System.out.println(" ID: admin / PW: 1234");
            System.out.println("======================================");
        }
    }
}