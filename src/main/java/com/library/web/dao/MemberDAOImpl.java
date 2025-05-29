package com.library.web.dao;

import com.library.web.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException; // 정확한 예외 임포트
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper; // RowMapper 임포트
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet; // ResultSet 임포트
import java.sql.SQLException; // SQLException 임포트
import java.sql.Timestamp; // Timestamp 임포트
import java.util.Date; // Date 임포트

@Repository
public class MemberDAOImpl implements MemberDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemberVO getMemberById(String memberId) throws Exception {
        String sql = "SELECT member_id, password, name, email, phone, address, role, status, regDate FROM member WHERE member_id = ?";
        try {
            // RowMapper 람다식을 사용하여 MemberVO 매핑
            return jdbcTemplate.queryForObject(sql, new Object[]{memberId}, (rs, rowNum) -> {
                MemberVO member = new MemberVO();
                member.setMemberId(rs.getString("member_id"));
                member.setPassword(rs.getString("password"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setRole(rs.getString("role"));
                member.setStatus(rs.getString("status"));
                member.setPhone(rs.getString("phone"));

                // address 필드는 NULL일 수 있으므로 예외 처리 없이 직접 할당 가능
                member.setAddress(rs.getString("address"));

                // --- 여기를 수정합니다. (regDate를 Date 타입으로) ---
                Timestamp regTimestamp = rs.getTimestamp("regDate");
                if (regTimestamp != null) {
                    member.setRegDate(new Date(regTimestamp.getTime())); // Timestamp를 Date로 변환하여 할당
                } else {
                    member.setRegDate(null);
                }
                // --- 수정 끝 ---
                return member;
            });
        } catch (EmptyResultDataAccessException e) { // EmptyResultDataAccessException을 명시적으로 처리
            return null; // 결과가 없을 경우 null 반환
        } catch (Exception e) {
            e.printStackTrace();
            throw e; // 다른 예외는 다시 던짐
        }
    }

    @Override
    public MemberVO login(String memberId, String password) throws Exception {
        // 실제 비밀번호 검증 로직이 여기에 추가되어야 합니다.
        // 현재는 ID만으로 회원 정보를 가져옵니다.
        // 예를 들어:
        // MemberVO member = getMemberById(memberId);
        // if (member != null && passwordEncoder.matches(password, member.getPassword())) {
        //     return member;
        // }
        // return null;
        return getMemberById(memberId); // 임시 반환
    }

    @Override
    public int updateMember(MemberVO member) throws Exception {
        String sql = "UPDATE member SET name = ?, email = ?, phone = ?, address = ?, role = ?, status = ?";
        boolean updatePassword = (member.getPassword() != null && !member.getPassword().isEmpty());

        if (updatePassword) {
            sql += ", password = ?";
        }
        sql += " WHERE member_id = ?";

        Object[] params;
        if (updatePassword) {
            params = new Object[]{
                member.getName(), member.getEmail(), member.getPhone(), member.getAddress(),
                member.getRole(), member.getStatus(),
                member.getPassword(),
                member.getMemberId()
            };
        } else {
            params = new Object[]{
                member.getName(), member.getEmail(), member.getPhone(), member.getAddress(),
                member.getRole(), member.getStatus(),
                member.getMemberId()
            };
        }
        return jdbcTemplate.update(sql, params);
    }

    @Override
    public void registerMember(MemberVO memberVO) throws Exception {
        String sql = "INSERT INTO member (member_id, password, name, email, phone, address, role, status, regDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        jdbcTemplate.update(sql,
                memberVO.getMemberId(),
                memberVO.getPassword(),
                memberVO.getName(),
                memberVO.getEmail(),
                memberVO.getPhone(),
                memberVO.getAddress(),
                memberVO.getRole() != null ? memberVO.getRole() : "USER",
                memberVO.getStatus() != null ? memberVO.getStatus() : "ACTIVE"
        );
    }

    @Override
    public boolean isMemberIdExists(String memberId) throws Exception {
        String sql = "SELECT COUNT(*) FROM member WHERE member_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, memberId);
        return count != null && count > 0;
    }
}