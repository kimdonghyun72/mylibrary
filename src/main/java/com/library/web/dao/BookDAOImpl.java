package com.library.web.dao; // BookDAOImpl의 실제 패키지 경로에 맞게 수정해주세요.

import com.library.web.vo.BookVO; // BookVO 클래스 경로 확인
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository // 이 클래스가 DAO 계층임을 스프링에 알립니다.
public class BookDAOImpl implements BookDAO { // BookDAO 인터페이스를 구현합니다.

    private final JdbcTemplate jdbcTemplate;

    // 생성자 주입 방식으로 JdbcTemplate을 받습니다.
    @Autowired
    public BookDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ResultSet의 결과를 BookVO 객체로 매핑하는 내부 클래스
    private static class BookRowMapper implements RowMapper<BookVO> {
        @Override
        public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BookVO book = new BookVO();
            // 데이터베이스 컬럼명과 BookVO의 필드명에 맞춰 매핑합니다.
            // 컬럼명은 DB의 실제 컬럼명과 대소문자까지 정확히 일치해야 합니다.
            book.setSeq_no(rs.getInt("seq_no"));
            book.setTitle_nm(rs.getString("title_nm"));
            book.setAuthr_nm(rs.getString("authr_nm"));
            book.setPublisher_nm(rs.getString("publisher_nm"));
            book.setBook_intrcn_cn(rs.getString("book_intrcn_cn")); // '책 소개 내용' 컬럼
            book.setCover_image(rs.getString("cover_image"));       // '커버 이미지 파일명' 컬럼
            book.setTwo_pblicte_de(rs.getString("two_pblicte_de")); // 'two_pblicte_de' (출판일) 컬럼 (VARCHAR(8) 가정)

            // BookVO에 없는 'portal_site_book_exst_at' 컬럼은 여기에서 매핑하지 않습니다.
            // 만약 VO에 필드를 추가하고 매핑하고 싶다면 rs.getString("portal_site_book_exst_at")로 가져올 수 있습니다.
            return book;
        }
    }

    // 모든 도서 목록을 가져오는 메소드
    @Override
    public List<BookVO> getAllBooks() throws Exception {
        // SQL 쿼리에서 테이블 이름은 'book' (단수), 컬럼 이름은 데이터베이스와 동일하게 씁니다.
        String sql = "SELECT seq_no, title_nm, authr_nm, publisher_nm, book_intrcn_cn, cover_image, two_pblicte_de, portal_site_book_exst_at FROM book";
        return jdbcTemplate.query(sql, new BookRowMapper());
    }

    // 도서 ID (seq_no)로 특정 도서 정보를 가져오는 메소드
    @Override
    public BookVO getBookById(int bookId) throws Exception {
        // SQL 쿼리에서 테이블 이름, 컬럼 이름 수정. WHERE 절도 'seq_no'로 변경.
        String sql = "SELECT seq_no, title_nm, authr_nm, publisher_nm, book_intrcn_cn, cover_image, two_pblicte_de, portal_site_book_exst_at FROM book WHERE seq_no = ?";
        return jdbcTemplate.queryForObject(sql, new BookRowMapper(), bookId);
    }

    // 최근 등록된 도서 목록을 가져오는 메소드 (신작)
    @Override
    public List<BookVO> getRecentBooks(int limit) throws Exception {
        // SQL 쿼리에서 테이블 이름, 컬럼 이름 수정. 정렬 기준도 'two_pblicte_de'와 'seq_no'로 변경.
        String sql = "SELECT seq_no, title_nm, authr_nm, publisher_nm, book_intrcn_cn, cover_image, two_pblicte_de, portal_site_book_exst_at " +
                     "FROM book " +
                     "ORDER BY two_pblicte_de DESC, seq_no DESC " + // 출판일(two_pblicte_de) 기준 최신, 같은 날짜면 seq_no 내림차순
                     "LIMIT ?";

        return jdbcTemplate.query(sql, new BookRowMapper(), limit);
    }

    // 추천 도서 목록을 가져오는 메소드 (랜덤 정렬)
    @Override
    public List<BookVO> getRecommendedBooks(int limit) throws Exception {
        // SQL 쿼리에서 테이블 이름, 컬럼 이름 수정.
        String sql = "SELECT seq_no, title_nm, authr_nm, publisher_nm, book_intrcn_cn, cover_image, two_pblicte_de, portal_site_book_exst_at " +
                     "FROM book " +
                     "ORDER BY RAND() " + // MySQL/MariaDB용 랜덤 함수 (다른 DB는 다를 수 있음)
                     "LIMIT ?";

        return jdbcTemplate.query(sql, new BookRowMapper(), limit);
    }

    // 키워드로 도서를 검색하는 메소드 (가장 중요!)
    @Override
    public List<BookVO> searchBooks(String keyword) {
        // SQL 쿼리에서 테이블 이름, 컬럼 이름 수정.
        // LIKE 절에 %를 붙여 부분 일치를 검색하고, keyword는 파라미터로 전달
        String sql = "SELECT seq_no, title_nm, authr_nm, publisher_nm, book_intrcn_cn, cover_image, two_pblicte_de, portal_site_book_exst_at " +
                     "FROM book " + // 테이블 이름 확인 (book 또는 books)
                     "WHERE title_nm LIKE ? OR authr_nm LIKE ? " +
                     "ORDER BY two_pblicte_de DESC, seq_no DESC " +
                     "LIMIT 100"; // 너무 많은 결과 방지 (원하는 만큼 조절)

        String searchKeyword = "%" + keyword + "%"; // %를 앞뒤에 붙여 부분 일치 검색
        return jdbcTemplate.query(sql, new BookRowMapper(), searchKeyword, searchKeyword);
    }


    // 도서 등록 메소드 (관리자 기능)
    @Override
    public void insertBook(BookVO book) throws Exception {
        // INSERT 쿼리에서 테이블 이름, 컬럼 이름 수정. 'reg_date', 'isbn' 등 없는 컬럼 제외.
        String sql = "INSERT INTO book (seq_no, title_nm, authr_nm, publisher_nm, book_intrcn_cn, cover_image, two_pblicte_de, portal_site_book_exst_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                book.getSeq_no(),
                book.getTitle_nm(),
                book.getAuthr_nm(),
                book.getPublisher_nm(),
                book.getBook_intrcn_cn(),
                book.getCover_image(),
                book.getTwo_pblicte_de(),
                "Y" // 'portal_site_book_exst_at'는 임시로 'Y' 고정, 실제 로직에 맞게 변경
        );
    }

    // 도서 정보 수정 메소드 (관리자 기능)
    @Override
    public void updateBook(BookVO book) throws Exception {
        // UPDATE 쿼리에서 테이블 이름, 컬럼 이름 수정. WHERE 절도 'seq_no'로 변경.
        String sql = "UPDATE book SET title_nm=?, authr_nm=?, publisher_nm=?, book_intrcn_cn=?, cover_image=?, two_pblicte_de=?, portal_site_book_exst_at=? WHERE seq_no=?";
        jdbcTemplate.update(sql,
                book.getTitle_nm(),
                book.getAuthr_nm(),
                book.getPublisher_nm(),
                book.getBook_intrcn_cn(),
                book.getCover_image(),
                book.getTwo_pblicte_de(),
                "Y", // 'portal_site_book_exst_at'는 임시로 'Y' 고정, 실제 로직에 맞게 변경
                book.getSeq_no()
        );
    }

    // 도서 삭제 메소드 (관리자 기능)
    @Override
    public void deleteBook(int bookId) throws Exception {
        // DELETE 쿼리에서 테이블 이름, WHERE 절도 'seq_no'로 변경.
        String sql = "DELETE FROM book WHERE seq_no=?";
        jdbcTemplate.update(sql, bookId);
    }
}