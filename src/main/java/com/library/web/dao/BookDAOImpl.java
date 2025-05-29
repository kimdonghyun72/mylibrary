package com.library.web.dao;

import com.library.web.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // BookVO 매핑을 위한 RowMapper (중복 코드 방지)
    private static class BookRowMapper implements RowMapper<BookVO> {
        @Override
        public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BookVO book = new BookVO();
            book.setBookId(rs.getInt("book_id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPublisher(rs.getString("publisher"));
            if (rs.getTimestamp("publication_date") != null) {
                book.setPublicationDate(rs.getTimestamp("publication_date").toLocalDateTime());
            }
            book.setIsbn(rs.getString("isbn"));
            book.setGenre(rs.getString("genre"));
            book.setDescription(rs.getString("description"));
            book.setTotalCount(rs.getInt("total_count"));
            book.setCurrentCount(rs.getInt("current_count"));
            if (rs.getTimestamp("reg_date") != null) {
                book.setRegDate(rs.getTimestamp("reg_date").toLocalDateTime());
            }
            // if (rs.getString("cover_image") != null) { // 이미지 필드가 있다면
            //     book.setCoverImage(rs.getString("cover_image"));
            // }
            return book;
        }
    }

    @Override
    public List<BookVO> getAllBooks() throws Exception {
        String sql = "SELECT book_id, title, author, publisher, publication_date, isbn, genre, description, total_count, current_count, reg_date FROM books";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    @Override
    public BookVO getBookById(int bookId) throws Exception {
        String sql = "SELECT book_id, title, author, publisher, publication_date, isbn, genre, description, total_count, current_count, reg_date FROM books WHERE book_id = ?";
        return jdbcTemplate.queryForObject(sql, new BookRowMapper(), bookId);
    }

    @Override
    public List<BookVO> getRecentBooks(int limit) throws Exception {
        String sql = "SELECT book_id, title, author, publisher, publication_date, isbn, genre, description, total_count, current_count, reg_date " +
                     "FROM books " +
                     "ORDER BY reg_date DESC " + // 등록일 기준 최신
                     "LIMIT ?"; // MySQL/MariaDB용 LIMIT. Oracle은 ROWNUM 사용

        return jdbcTemplate.query(sql, new BookRowMapper(), limit);
    }

    @Override
    public List<BookVO> getRecommendedBooks(int limit) throws Exception {
        // 간단한 추천 로직: 랜덤으로 가져오기
        String sql = "SELECT book_id, title, author, publisher, publication_date, isbn, genre, description, total_count, current_count, reg_date " +
                     "FROM books " +
                     "ORDER BY RAND() " + // MySQL/MariaDB의 랜덤 함수. 다른 DB는 문법이 다름
                     "LIMIT ?";

        return jdbcTemplate.query(sql, new BookRowMapper(), limit);
    }

    @Override
    public void insertBook(BookVO book) throws Exception {
        String sql = "INSERT INTO books (title, author, publisher, publication_date, isbn, genre, description, total_count, current_count, reg_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublisher(),
                            book.getPublicationDate(), book.getIsbn(), book.getGenre(),
                            book.getDescription(), book.getTotalCount(), book.getCurrentCount());
    }

    @Override
    public void updateBook(BookVO book) throws Exception {
        String sql = "UPDATE books SET title=?, author=?, publisher=?, publication_date=?, isbn=?, genre=?, description=?, total_count=?, current_count=? WHERE book_id=?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublisher(),
                            book.getPublicationDate(), book.getIsbn(), book.getGenre(),
                            book.getDescription(), book.getTotalCount(), book.getCurrentCount(), book.getBookId());
    }

    @Override
    public void deleteBook(int bookId) throws Exception {
        String sql = "DELETE FROM books WHERE book_id=?";
        jdbcTemplate.update(sql, bookId);
    }
}