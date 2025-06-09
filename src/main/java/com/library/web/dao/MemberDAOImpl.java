package com.library.web.dao;

import com.library.web.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@Repository
public class MemberDAOImpl implements MemberDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemberVO getMemberById(String memberId) throws Exception {
    	System.out.println("333333");
        String sql = "SELECT * FROM member WHERE member_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            	System.out.println("첫번째");
                MemberVO member = new MemberVO();
                member.setMemberId(rs.getString("member_id"));
                member.setPassword(rs.getString("password"));
                member.setName(rs.getString("name"));
                member.setEmail(rs.getString("email"));
                member.setRole(rs.getString("role"));
                member.setStatus(rs.getString("status"));
                member.setPhone(rs.getString("phone"));

                member.setAddress(rs.getString("address"));

                Timestamp regTimestamp = rs.getTimestamp("regDate");
                if (regTimestamp != null) {
                    member.setRegDate(new Date(regTimestamp.getTime()));
                } else {
                    member.setRegDate(null);
                }
                return member;
            }, memberId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public MemberVO login(String memberId, String password) throws Exception {
        // 실제 비밀번호 검증 로직이 여기에 추가되어야 합니다.
        // 현재는 ID만으로 회원 정보를 가져옵니다.
        // MemberServiceImpl에서 비밀번호 비교를 수행하고 있으므로,
        // DAO에서는 단순히 MemberVO를 ID로 가져오는 getMemberById를 호출하는 것이 일반적입니다.
        // 비밀번호는 MemberService 계층에서 비교하는 것이 좋습니다.
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
    public int registerMember(MemberVO memberVO) throws Exception { // <-- 반환 타입을 int로 수정했습니다.
        String sql = "INSERT INTO member (member_id, password, name, email, phone, address, role, status, regDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        // jdbcTemplate.update()는 영향을 받은 행의 수를 int로 반환합니다.
        // 이 값을 그대로 반환하도록 수정했습니다.
        return jdbcTemplate.update(sql,
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