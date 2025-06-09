package com.library.web.dao;

import com.library.web.vo.NoticeVO;
import com.library.web.dao.NoticeDAO; // 이 줄은 필요 없는 경우가 많습니다 (동일 패키지 내부라면)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.time.LocalDateTime; // LocalDateTime을 사용하므로 임포트 필요

@Repository
public class NoticeDAOImpl implements NoticeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NoticeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class NoticeRowMapper implements RowMapper<NoticeVO> {
        @Override
        public NoticeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NoticeVO notice = new NoticeVO();
            notice.setNoticeId(rs.getInt("notice_id"));       
            notice.setTitle(rs.getString("title"));
            notice.setContent(rs.getString("content"));
            
            // reg_date 매핑
            if (rs.getTimestamp("reg_date") != null) {
                notice.setRegDate(rs.getTimestamp("reg_date").toLocalDateTime());
            } else {
                notice.setRegDate(null); // 또는 적절한 기본값
            }
            
            notice.setViewCount(rs.getInt("view_count"));

            // --- 이 부분을 추가해야 합니다: mod_date 매핑 ---
            if (rs.getTimestamp("mod_date") != null) {
                notice.setModDate(rs.getTimestamp("mod_date").toLocalDateTime());
            } else {
                notice.setModDate(null); // 또는 적절한 기본값
            }
            // ---------------------------------------------
            
            return notice;
        }
    }

    @Override
    public void insertNotice(NoticeVO noticeVO) throws Exception {
        // mod_date도 DB에 있으므로, insert 쿼리에도 추가하는 것이 좋습니다.
        // 물론 DB에서 mod_date에 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP 같은 설정이 되어 있다면 쿼리에서 뺄 수도 있습니다.
        String sql = "INSERT INTO notices (title, content, reg_date, view_count, mod_date) VALUES (?, ?, NOW(), 0, NOW())";
        jdbcTemplate.update(sql, noticeVO.getTitle(), noticeVO.getContent());
    }

    @Override
    public NoticeVO getNoticeById(int noticeId) throws Exception {
        String sql = "SELECT * FROM notices WHERE notice_id = ?";
        return jdbcTemplate.queryForObject(sql, new NoticeRowMapper(), noticeId);
    }

    @Override
    public void updateNotice(NoticeVO noticeVO) throws Exception {
        // mod_date도 업데이트 쿼리에 포함하여, 업데이트 시 갱신되도록 하는 것이 좋습니다.
        String sql = "UPDATE notices SET title = ?, content = ?, mod_date = NOW() WHERE notice_id = ?";
        jdbcTemplate.update(sql, noticeVO.getTitle(), noticeVO.getContent(), noticeVO.getNoticeId());
    }

    @Override
    public void deleteNotice(int noticeId) throws Exception {
        String sql = "DELETE FROM notices WHERE notice_id = ?";
        jdbcTemplate.update(sql, noticeId);
    }

    @Override
    public void increaseViewCount(int noticeId) throws Exception {
        String sql = "UPDATE notices SET view_count = view_count + 1 WHERE notice_id = ?";
        jdbcTemplate.update(sql, noticeId);
    }

    @Override
    public int getTotalCount() throws Exception {
        String sql = "SELECT COUNT(*) FROM notices";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<NoticeVO> getNoticesByPage(int offset, int limit) throws Exception {
        // 이 쿼리는 모든 컬럼을 가져오므로, mod_date가 정상적으로 포함되어 매핑될 수 있습니다.
        String sql = "SELECT * FROM notices ORDER BY reg_date DESC LIMIT ?, ?";
        return jdbcTemplate.query(sql, new NoticeRowMapper(), offset, limit);
        
    }
}