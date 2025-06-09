package com.library.web.controller;

import com.library.web.service.BookService;
import com.library.web.service.QnaService;
import com.library.web.service.NoticeService;
import com.library.web.dto.NoticeDto;
import com.library.web.vo.NoticeVO;
import com.library.web.vo.BookVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; // import 추가 확인

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/mylibrary") // <--- 이 주석을 제거하여 활성화했습니다!
public class HomeController {

    private final BookService bookService;
    private final QnaService qnaService;
    private final NoticeService noticeService;

    @Autowired
    public HomeController(BookService bookService, QnaService qnaService, NoticeService noticeService) {
        this.bookService = bookService;
        this.qnaService = qnaService;
        this.noticeService = noticeService;
    }

    // 이 @GetMapping은 @RequestMapping("/mylibrary")와 결합하여
    // /mylibrary/ 또는 /mylibrary 경로를 처리합니다.
    @GetMapping({"/", ""}) // 이제 이 경로는 /mylibrary/ 와 /mylibrary 에 매핑됩니다.
    public String home(Model model) {
        System.out.println("########## HomeController의 home() 메서드 실행 시작! ##########");

        // 팝업 공지사항 (기존 로직 유지)
        NoticeVO popupNotice = new NoticeVO();
        popupNotice.setTitle("마을 도서관 서비스 일시 중단 안내");
        popupNotice.setContent("<p>안녕하세요, 마을 도서관입니다.</p>"
                             + "<p>더 나은 서비스를 위해 <b>시스템 점검</b>이 예정되어 있습니다.</p>"
                             + "<p>점검 시간: <b>2025년 5월 31일 01:00 ~ 05:00 (KST)</b></p>"
                             + "<p>이 시간 동안 서비스 이용이 일시적으로 제한될 수 있으니 양해 부탁드립니다.</p>"
                             + "<p>감사합니다.</p>");
        model.addAttribute("popupNotice", popupNotice);

        // 최신 공지사항 가져오기 (DB에서)
        try {
            // NoticeService의 getNoticesByPage 메서드를 사용하여 최신 5개 공지사항을 가져옵니다.
            // (offset: 0, pageSize: 5)
            List<NoticeVO> recentNoticeVOs = noticeService.getNoticesByPage(0, 5);
            
            // 가져온 NoticeVO 리스트를 NoticeDto 리스트로 변환합니다.
            List<NoticeDto> recentNotices = recentNoticeVOs.stream()
                                             .map(NoticeDto::from)
                                             .collect(Collectors.toList());
            
            // 변환된 DTO 리스트를 모델에 "recentNotices" 이름으로 추가하여 JSP로 전달합니다.
            model.addAttribute("recentNotices", recentNotices);
            System.out.println("DEBUG: 최근 공지사항 수: " + recentNotices.size());

        } catch (Exception e) {
            // 공지사항 로딩 중 오류 발생 시, 스택 트레이스를 출력하고 빈 리스트를 모델에 추가합니다.
            e.printStackTrace();
            model.addAttribute("recentNotices", new ArrayList<NoticeDto>());
            System.err.println("ERROR: 최근 공지사항 가져오기 실패: " + e.getMessage());
        }

        // 최근 Q&A 가져오기 (DB에서)
        try {
            // qnaService.getRecentQnAs(5) 호출하여 최신 Q&A를 가져옵니다.
            model.addAttribute("recentQnAs", qnaService.getRecentQnAs(5));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("recentQnAs", new ArrayList<>()); // 오류 발생 시 빈 리스트
        }

        // 신작 도서 가져오기 (DB에서)
        try {
            // bookService.getRecentBooks(6) 호출하여 신작 도서 6개를 가져옵니다.
            List<BookVO> newBooks = bookService.getRecentBooks(6);
            model.addAttribute("newBooks", newBooks);
            System.out.println("DEBUG: 신작 도서 수: " + (newBooks != null ? newBooks.size() : "null 리스트"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("newBooks", new ArrayList<BookVO>()); // 오류 발생 시 빈 리스트
            System.err.println("ERROR: 신작 도서 가져오기 실패: " + e.getMessage());
        }

        // 추천 도서 가져오기 (DB에서)
        try {
            // bookService.getRecommendedBooks(6) 호출하여 추천 도서 6개를 가져옵니다.
            List<BookVO> recommendedBooks = bookService.getRecommendedBooks(6);
            model.addAttribute("recommendedBooks", recommendedBooks);
            System.out.println("DEBUG: 추천 도서 수: " + (recommendedBooks != null ? recommendedBooks.size() : "null 리스트"));
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("recommendedBooks", new ArrayList<BookVO>()); // 오류 발생 시 빈 리스트
            System.err.println("ERROR: 추천 도서 가져오기 실패: " + e.getMessage());
        }

        // 에러 및 메시지 속성 초기화 (JSP에서 활용 가능)
        model.addAttribute("errorMessage", null);
        model.addAttribute("message", null);

        // "index.jsp" 뷰를 반환합니다. 이 뷰에서 "recentNotices"를 사용하게 됩니다.
        return "index";
    }
}