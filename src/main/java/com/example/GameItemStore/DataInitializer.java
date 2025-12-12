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
        if (memberRepository.findByLoginId("admin").isEmpty()) {

            Member admin = new Member();
            admin.setLoginId("admin");
            admin.setPassword("1234");
            admin.setNickname("총관리자");
            admin.setRole("ADMIN");
            admin.setMileage(1000000);

            memberRepository.save(admin);

            System.out.println("======================================");
            System.out.println(" [알림] 관리자 계정이 생성되었습니다!");
            System.out.println(" ID: admin / PW: 1234");
            System.out.println("======================================");
        }
    }
}