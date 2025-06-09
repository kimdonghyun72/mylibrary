package com.library.web.service;

import com.library.web.dao.MemberDAO;
import com.library.web.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 암호화 사용 안 할 경우 임포트 제거
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDAO memberDAO;
    // private final BCryptPasswordEncoder passwordEncoder; // 암호화 사용 안 할 경우 주석 처리 또는 제거

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO /* , BCryptPasswordEncoder passwordEncoder */) { // 생성자에서 주입 제거
        this.memberDAO = memberDAO;
        // this.passwordEncoder = passwordEncoder; // 암호화 사용 안 할 경우 주석 처리 또는 제거
    }

    @Override
    public void registerMember(MemberVO memberVO) throws Exception { // <-- 반환 타입을 boolean -> void로 수정했습니다.
        // 1. 아이디 중복 확인
        if (memberDAO.isMemberIdExists(memberVO.getMemberId())) {
            throw new Exception("이미 존재하는 아이디입니다."); // 중복 시 예외 발생
        }

        // 2. 비밀번호 암호화 로직 제거 (평문 비밀번호 그대로 DAO에 전달)
        // String encodedPassword = passwordEncoder.encode(memberVO.getPassword());
        // memberVO.setPassword(encodedPassword);

        // 3. DAO를 통해 DB에 저장
        // DAO의 registerMember가 성공적으로 실행되면 (예: 1을 반환하면), 별도로 boolean을 반환할 필요 없이 다음 단계로 넘어갑니다.
        // 만약 DAO에서 예외를 던지거나, 반환값이 0인 경우 실패로 간주하고 싶다면 이곳에서 예외를 다시 던질 수 있습니다.
        int result = memberDAO.registerMember(memberVO);
        if (result != 1) { // 1이 아니면 정상적으로 삽입되지 않았다고 판단
            throw new Exception("회원가입 처리 중 오류가 발생했습니다.");
        }
        // return result == 1; // <-- 이 줄은 제거했습니다. (void 타입이기 때문)
    }

    @Override
    public boolean isMemberIdExists(String memberId) throws Exception {
        return memberDAO.isMemberIdExists(memberId);
    }

    @Override
    public MemberVO login(String memberId, String password) throws Exception {
        MemberVO member = memberDAO.getMemberById(memberId);

        // 1. 회원 ID가 존재하는지 확인
        if (member == null) {
            return null; // 아이디가 존재하지 않음
        }

        // 2. 입력된 비밀번호와 DB에 저장된 평문 비밀번호 직접 비교
        // ⭐ 매우 중요: 실제 서비스에서는 이 부분을 다시 암호화 비교로 변경해야 합니다.
        if (password.equals(member.getPassword())) { // 평문 비밀번호 직접 비교
            return member; // 로그인 성공
        } else {
            return null; // 비밀번호 불일치
        }
    }

    @Override
    public MemberVO getMemberById(String memberId) throws Exception {
        return memberDAO.getMemberById(memberId);
    }

    @Override
    public boolean updateMember(MemberVO member) throws Exception {
        // 비밀번호가 입력되었다면 평문 비밀번호 그대로 설정
        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            // 암호화 로직 제거
            // String encodedPassword = passwordEncoder.encode(member.getPassword());
            // member.setPassword(encodedPassword);
            // MemberVO에 이미 평문 비밀번호가 설정되어 있을 것이므로 추가 처리 불필요
        } else {
            // 비밀번호 필드를 비워두면 기존 비밀번호 유지
            // (만약 DAO에서 password를 무조건 업데이트한다면, 여기에서 기존 password를 가져와 다시 set 해야 함)
            // MemberVO existingMember = memberDAO.getMemberById(member.getMemberId());
            // if (existingMember != null) {
            //      member.setPassword(existingMember.getPassword());
            // }
        }

        int updatedRows = memberDAO.updateMember(member);
        return updatedRows > 0;
    }
}