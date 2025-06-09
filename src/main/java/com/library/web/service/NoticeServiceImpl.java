package com.library.web.service;

import com.library.web.dao.NoticeDAO;
import com.library.web.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections; // Collections.emptyList()를 사용하기 위해 추가
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDAO noticeDAO;

    @Autowired
    public NoticeServiceImpl(NoticeDAO noticeDAO) {
        this.noticeDAO = noticeDAO;
    }

    @Override
    @Transactional // 트랜잭션 처리가 필요할 때 사용 (INSERT, UPDATE, DELETE)
    public void registerNotice(NoticeVO noticeVO) throws Exception {
        try {
            noticeDAO.insertNotice(noticeVO);
            System.out.println("DEBUG in NoticeServiceImpl: 공지사항 등록 성공 - " + noticeVO.getTitle());
        } catch (Exception e) {
            System.err.println("ERROR in NoticeServiceImpl - registerNotice: 공지사항 등록 실패. " + e.getMessage());
            e.printStackTrace();
            throw e; // 예외를 다시 던져서 상위 계층에서 처리하도록 함
        }
    }

    @Override
    @Transactional
    public NoticeVO getNoticeDetail(int noticeId) throws Exception {
        NoticeVO notice = null;
        try {
            noticeDAO.increaseViewCount(noticeId); // 조회수 증가
            notice = noticeDAO.getNoticeById(noticeId);
            System.out.println("DEBUG in NoticeServiceImpl: 공지사항 상세 조회 성공 - ID: " + noticeId + ", 제목: " + (notice != null ? notice.getTitle() : "없음"));
        } catch (Exception e) {
            System.err.println("ERROR in NoticeServiceImpl - getNoticeDetail: 공지사항 상세 조회 실패. ID: " + noticeId + ", " + e.getMessage());
            e.printStackTrace();
            // 여기서는 null을 반환하거나, 적절한 예외 처리를 할 수 있습니다.
            // 현재는 상위로 예외를 던지도록 설정.
            throw e;
        }
        return notice;
    }

    @Override
    @Transactional
    public void modifyNotice(NoticeVO noticeVO) throws Exception {
        try {
            noticeDAO.updateNotice(noticeVO);
            System.out.println("DEBUG in NoticeServiceImpl: 공지사항 수정 성공 - ID: " + noticeVO.getNoticeId());
        } catch (Exception e) {
            System.err.println("ERROR in NoticeServiceImpl - modifyNotice: 공지사항 수정 실패. ID: " + noticeVO.getNoticeId() + ", " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    @Transactional
    public void removeNotice(int noticeId) throws Exception {
        try {
            noticeDAO.deleteNotice(noticeId);
            System.out.println("DEBUG in NoticeServiceImpl: 공지사항 삭제 성공 - ID: " + noticeId);
        } catch (Exception e) {
            System.err.println("ERROR in NoticeServiceImpl - removeNotice: 공지사항 삭제 실패. ID: " + noticeId + ", " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public int getTotalNoticeCount() throws Exception {
        int count = 0;
        try {
            count = noticeDAO.getTotalCount();
            System.out.println("DEBUG in NoticeServiceImpl: 전체 공지사항 수 조회 성공 - " + count);
        } catch (Exception e) {
            System.err.println("ERROR in NoticeServiceImpl - getTotalNoticeCount: 전체 공지사항 수 조회 실패. " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return count;
    }

    @Override
    public List<NoticeVO> getNoticesByPage(int offset, int limit) throws Exception {
        List<NoticeVO> notices = Collections.emptyList(); // 초기값을 빈 리스트로 설정
        try {
            notices = noticeDAO.getNoticesByPage(offset, limit);
            // ⭐⭐⭐ DAO에서 반환된 리스트의 크기를 출력하는 가장 중요한 로그 ⭐⭐⭐
            System.out.println("DEBUG in NoticeServiceImpl: DAO로부터 받은 공지사항 수 (offset: " + offset + ", limit: " + limit + "): " + (notices != null ? notices.size() : "null"));
        } catch (Exception e) {
            // 예외 발생 시 로그 출력 및 빈 리스트 반환 (호출하는 곳에서 NullPointerException 방지)
            System.err.println("ERROR in NoticeServiceImpl - getNoticesByPage: 공지사항 페이지 조회 실패. " + e.getMessage());
            e.printStackTrace();
            // 여기서 예외를 다시 던지지 않고 빈 리스트를 반환하여 호출자(HomeController)가 정상 흐름을 유지하도록 함
            // 만약 예외가 발생하면 홈페이지에서 아예 오류 페이지를 보여주고 싶다면 `throw e;`를 유지
            // 현재는 "공지사항이 없습니다" 메시지가 나오도록 빈 리스트를 반환하는 것이 목표이므로 이렇게 처리.
            return Collections.emptyList();
        }
        return notices;
    }
}