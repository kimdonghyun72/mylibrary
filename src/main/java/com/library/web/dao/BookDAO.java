package com.library.web.dao;

import com.library.web.vo.BookVO;
import java.util.List;

public interface BookDAO {
    // 모든 책 목록 가져오기 (필요하다면)
    List<BookVO> getAllBooks() throws Exception;

    // 책 ID로 특정 책 정보 가져오기 (필요하다면)
    BookVO getBookById(int bookId) throws Exception;

    // 최근 등록된 책 목록 가져오기 (신작)
    List<BookVO> getRecentBooks(int limit) throws Exception;

    // 추천 도서 목록 가져오기 (랜덤 정렬)
    List<BookVO> getRecommendedBooks(int limit) throws Exception;

    // 책 등록 (관리자 기능, 필요하다면)
    void insertBook(BookVO book) throws Exception;

    // 책 정보 수정 (관리자 기능, 필요하다면)
    void updateBook(BookVO book) throws Exception;

    // 책 삭제 (관리자 기능, 필요하다면)
    void deleteBook(int bookId) throws Exception;
}