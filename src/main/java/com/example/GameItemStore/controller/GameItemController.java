package com.example.GameItemStore.controller;

import com.example.GameItemStore.entiy.GameItem; // 회원님이 만드신 'entiy' 패키지 이름 그대로 씀
import com.example.GameItemStore.service.GameItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 스프링에게 "이건 웹사이트 화면 관리하는 애야"라고 알려줌
public class GameItemController {

    @Autowired // "서비스(일 처리하는 애) 데려와줘"
    private GameItemService gameItemService;

    // 1. 아이템 등록 화면 보여주기 (GET 방식)
    // 사용자가 인터넷 주소창에 "localhost:8080/add" 라고 치면 이 기능이 실행됨
    @GetMapping("/add")
    public String showAddForm() {
        // "add.html 이라는 화면 파일을 찾아서 보여줘라" 라는 뜻
        return "add";
    }

    // 2. 아이템 실제로 저장하기 (POST 방식)
    // 사용자가 화면에서 [저장] 버튼을 누르면 이 기능이 실행됨
    @PostMapping("/add")
    public String saveItem(GameItem gameItem) {
        // HTML 화면에서 입력한 데이터(이름, 가격 등)가 gameItem 변수에 쏙 들어와 있음

        // 서비스에게 "이거 DB에 좀 저장해줘" 라고 시킴
        gameItemService.write(gameItem);

        // 저장이 끝나면 "/list" 페이지로 강제 이동시킴 (새로고침 효과)
        return "redirect:/list";
    }

    // 3. 아이템 목록 보여주기 (GET 방식)
    // 사용자가 "localhost:8080/list" 라고 치면 실행됨
    @GetMapping("/list")
    public String showList(Model model) {
        // 서비스에게 "모든 아이템 다 가져와" 라고 시킴
        // model.addAttribute("이름", 데이터): 화면(HTML)으로 데이터를 넘겨주는 택배 상자
        model.addAttribute("items", gameItemService.gameItemList());

        // "list.html 화면을 보여줘"
        return "list";
    }
}