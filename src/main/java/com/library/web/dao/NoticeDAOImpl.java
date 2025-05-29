package com.library.web.dao;

import com.library.web.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NoticeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<NoticeVO> getRecentNotices(int limit) throws Exception {
        String sql = "SELECT notice_id, title, content, reg_date, views " +
                     "FROM notices " +
                     "ORDER BY reg_date DESC " +
                     "LIMIT ?"; // LIMIT 절은 DB마다 다를 수 있음 (예: MySQL/MariaDB는 LIMIT, Oracle은 ROWNUM)

        return jdbcTemplate.query(sql, new RowMapper<NoticeVO>() {
            @Override
            public NoticeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                NoticeVO notice = new NoticeVO();
                notice.setNoticeId(rs.getInt("notice_id"));
                notice.setTitle(rs.getString("title"));
                notice.setContent(rs.getString("content"));
                if (rs.getTimestamp("reg_date") != null) {
                    notice.setRegDate(rs.getTimestamp("reg_date").toLocalDateTime());
                }
                notice.setViews(rs.getInt("views"));
                return notice;
            }
        }, limit);
    }
}