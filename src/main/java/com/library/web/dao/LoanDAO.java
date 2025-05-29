package com.library.web.dao;

import com.library.web.vo.LoanVO;
import org.springframework.stereotype.Repository; // @Repository 임포트

import java.util.List;

@Repository // 스프링이 이 인터페이스를 DAO 계층의 빈으로 인식하도록 함
public interface LoanDAO {
    // 특정 회원의 대출 목록을 조회하는 메서드
    List<LoanVO> getLoansByMemberId(String memberId) throws Exception;

    // TODO: 대출, 반납, 연장 등 다른 대출 관련 DAO 메서드들을 추가할 수 있습니다.
}