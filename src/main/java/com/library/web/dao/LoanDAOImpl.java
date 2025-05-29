package com.library.web.dao;

import com.library.web.vo.LoanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate; // JDBC Template 임포트
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime; // LocalDateTime 임포트
import java.util.List;

@Repository // 이 클래스를 DAO 구현체로 스프링 빈에 등록
public class LoanDAOImpl implements LoanDAO {

    private final JdbcTemplate jdbcTemplate; // JdbcTemplate 주입받음

    @Autowired
    public LoanDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LoanVO> getLoansByMemberId(String memberId) throws Exception {
        // SQL 쿼리 작성 (JOIN을 통해 도서 제목과 저자를 함께 가져옴)
        String sql = "SELECT " +
                     "    l.loan_id, l.member_id, l.book_id, l.loan_date, l.due_date, l.return_date, " +
                     "    b.title as book_title, b.author " +
                     "FROM " +
                     "    loans l " +
                     "JOIN " +
                     "    books b ON l.book_id = b.book_id " +
                     "WHERE " +
                     "    l.member_id = ? " + // PreparedStatement의 파라미터는 ? 로 표시
                     "ORDER BY " +
                     "    l.loan_date DESC";

        // JdbcTemplate.query() 메서드를 사용하여 List<LoanVO>를 반환
        // RowMapper를 구현하여 ResultSet의 각 로우를 LoanVO 객체로 매핑합니다.
        return jdbcTemplate.query(sql, new RowMapper<LoanVO>() {
            @Override
            public LoanVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                LoanVO loan = new LoanVO();
                loan.setLoanId(rs.getInt("loan_id"));
                loan.setMemberId(rs.getString("member_id"));
                loan.setBookId(rs.getInt("book_id"));

                // DB에서 가져온 Timestamp를 LocalDateTime으로 변환
                if (rs.getTimestamp("loan_date") != null) {
                    loan.setLoanDate(rs.getTimestamp("loan_date").toLocalDateTime());
                }
                if (rs.getTimestamp("due_date") != null) {
                    loan.setDueDate(rs.getTimestamp("due_date").toLocalDateTime());
                }
                if (rs.getTimestamp("return_date") != null) {
                    loan.setReturnDate(rs.getTimestamp("return_date").toLocalDateTime());
                }

                loan.setBookTitle(rs.getString("book_title"));
                loan.setAuthor(rs.getString("author"));
                return loan;
            }
        }, memberId); // SQL 쿼리의 ? 에 매핑될 값
    }

    // TODO: 다른 대출 관련 JDBC Template 메서드들을 여기에 추가할 수 있습니다.
    // public void insertLoan(LoanVO loanVO) throws Exception { ... }
    // public void updateLoanReturnDate(int loanId, LocalDateTime returnDate) throws Exception { ... }
}