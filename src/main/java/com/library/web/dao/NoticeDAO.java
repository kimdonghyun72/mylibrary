package com.library.web.dao;

import com.library.web.vo.NoticeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeDAO {
    // 최근 공지사항 N개를 가져오는 메서드
    List<NoticeVO> getRecentNotices(int limit) throws Exception;

    // TODO: 공지사항 상세 보기, 추가, 수정, 삭제 등 다른 메서드들을 추가할 수 있습니다.
    // NoticeVO getNoticeById(int noticeId) throws Exception;
    // void insertNotice(NoticeVO notice) throws Exception;
}