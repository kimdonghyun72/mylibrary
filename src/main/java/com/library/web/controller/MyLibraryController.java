package com.library.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; // @RequestMapping을 사용하기 위해 import 추가
import jakarta.servlet.http.HttpSession; // HttpSession을 사용하기 위해 import 추가
import org.springframework.ui.Model; // Model을 사용하기 위해 import 추가
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // RedirectAttributes를 사용하기 위해 import 추가


@Controller
@RequestMapping("/mylibrary") // 이 컨트롤러의 기본 경로를 /mylibrary로 설정합니다.
public class MyLibraryController {

    @GetMapping("/mypage") // /mylibrary/mypage 경로를 처리합니다.
    public String showMyLibraryPage(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("########## MyLibraryController의 showMyLibraryPage() 메서드 실행 시작! ##########");

        // 이 부분은 기존에 MyLibraryController에 로그인 확인 로직이 있었다면 유지합니다.
        // 만약 없다면, 이 로직은 삭제하셔도 됩니다.
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/member/login"; // 로그인 페이지로 리다이렉트
        }
        
        // 여기에 mylibrary/myPage.jsp에서 사용할 데이터(예: 대출 목록, 예약 목록 등)를 Model에 추가할 수 있습니다.
        // 예: List<LoanVO> currentLoans = loanService.getCurrentLoans(userId);
        // model.addAttribute("currentLoans", currentLoans);

        return "mylibrary/myPage"; // mylibrary 폴더 아래의 myPage.jsp를 렌더링합니다.
    }
}