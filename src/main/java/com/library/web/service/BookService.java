package com.library.web.service;

import com.library.web.vo.BookVO;
import java.util.List;

public interface BookService {
    List<BookVO> getAllBooks() throws Exception;
    BookVO getBookById(int bookId) throws Exception;
    List<BookVO> getRecentBooks(int limit) throws Exception;
    List<BookVO> getRecommendedBooks(int limit) throws Exception;
    void insertBook(BookVO book) throws Exception;
    void updateBook(BookVO book) throws Exception;
    void deleteBook(int bookId) throws Exception;
}