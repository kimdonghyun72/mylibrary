
package com.library.web.controller;

import com.library.web.service.NoticeService;
import com.library.web.service.BookService;
import com.library.web.vo.NoticeVO;
import com.library.web.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.ArrayList; // ArrayList 임포트

@Controller
public class HomeController {

    // NoticeService와 BookService는 일단 주석 처리하거나 빈 값으로 유지
    // @Autowired
    // private NoticeService noticeService;
    // @Autowired
    // private BookService bookService;

    // 생성자 주입 부분도 주석 처리 (Service 객체가 없으므로)
    // @Autowired
    // public HomeController(NoticeService noticeService, BookService bookService) {
    //     this.noticeService = noticeService;
    //     this.bookService = bookService;
    // }

    // 기본 생성자 추가 (Spring이 Service 의존성을 주입하지 못하더라도 에러 방지)
    public HomeController() {
        // 서비스 객체가 없으므로 빈 생성자 또는 null 처리
    }


    @GetMapping("/")
    public String home(Model model) {
        System.out.println("DEBUG: HomeController의 home() 메서드 실행됨. index.jsp 반환.");

        // 데이터베이스 연결 없이 빈 리스트를 강제로 Model에 추가하여 디자인 확인
        model.addAttribute("recentNotices", new ArrayList<NoticeVO>());
        model.addAttribute("newBooks", new ArrayList<BookVO>());
        model.addAttribute("recommendedBooks", new ArrayList<BookVO>());

        // 만약 임시 데이터를 넣어 디자인 테스트하고 싶다면 아래 주석 해제 및 데이터 추가
        /*
        List<NoticeVO> tempNotices = new ArrayList<>();
        tempNotices.add(new NoticeVO(1, "임시 공지사항 1", "내용1", LocalDateTime.now(), 0));
        tempNotices.add(new NoticeVO(2, "임시 공지사항 2", "내용2", LocalDateTime.now().minusDays(1), 0));
        model.addAttribute("recentNotices", tempNotices);

        List<BookVO> tempBooks = new ArrayList<>();
        tempBooks.add(new BookVO(1, "임시 신작 도서 1", "저자A", "출판사X", LocalDateTime.now(), "12345", "장르", "설명", 1,1,LocalDateTime.now()));
        tempBooks.add(new BookVO(2, "임시 신작 도서 2", "저자B", "출판사Y", LocalDateTime.now().minusDays(5), "67890", "장르", "설명", 1,1,LocalDateTime.now()));
        model.addAttribute("newBooks", tempBooks);
        model.addAttribute("recommendedBooks", tempBooks); // 추천도서도 같은 데이터로
        */


        model.addAttribute("errorMessage", null); // 에러 메시지 없음
        model.addAttribute("message", null); // 메시지 없음

        return "index"; // views/index.jsp 반환
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        return home(model); // home() 메서드를 호출하여 Model에 데이터를 담고 동일한 JSP를 반환
    }

    @GetMapping("/library")
    public String myLibrary(Model model) {
        System.out.println("DEBUG: HomeController의 myLibrary() 메서드 실행됨. mylibrary.jsp 반환.");
        return "mylibrary"; // views/mylibrary.jsp 반환
    }
}