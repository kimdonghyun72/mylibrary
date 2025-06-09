package com.library.web.dao;

import com.library.web.vo.MemberVO;

public interface MemberDAO {
    // 회원 정보를 받아 DB에 등록하는 메서드 (회원가입)
    // 이 메서드의 반환 타입을 void -> int로 수정했습니다.
    int registerMember(MemberVO memberVO) throws Exception;

    // 회원 ID로 회원 정보 조회
    MemberVO getMemberById(String memberId) throws Exception;

    // 로그인 기능: 아이디와 비밀번호로 회원 정보를 조회
    MemberVO login(String memberId, String password) throws Exception;

    // 회원 정보를 업데이트하는 메서드
    int updateMember(MemberVO member) throws Exception; // 이 메서드는 이미 int였습니다.

    // isMemberIdExists 메서드가 MemberDAOImpl에 있다면 인터페이스에도 추가해야 합니다.
    boolean isMemberIdExists(String memberId) throws Exception;
}