package com.library.web.service;

import com.library.web.dao.BookDAO;
import com.library.web.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;

    @Autowired
    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public List<BookVO> getAllBooks() throws Exception {
        return bookDAO.getAllBooks();
    }

    @Override
    public BookVO getBookById(int bookId) throws Exception {
        return bookDAO.getBookById(bookId);
    }

    @Override
    public List<BookVO> getRecentBooks(int limit) throws Exception {
        return bookDAO.getRecentBooks(limit);
    }

    @Override
    public List<BookVO> getRecommendedBooks(int limit) throws Exception {
        return bookDAO.getRecommendedBooks(limit);
    }

    @Override
    public void insertBook(BookVO book) throws Exception {
        bookDAO.insertBook(book);
    }

    @Override
    public void updateBook(BookVO book) throws Exception {
        bookDAO.updateBook(book);
    }

    @Override
    public void deleteBook(int bookId) throws Exception {
        bookDAO.deleteBook(bookId);
    }
}