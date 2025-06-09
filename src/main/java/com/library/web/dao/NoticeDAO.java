package com.library.web.dao;

import com.library.web.vo.NoticeVO;

import java.util.List;

public interface NoticeDAO {
    // 기존 메서드
    void insertNotice(NoticeVO noticeVO) throws Exception;
    NoticeVO getNoticeById(int noticeId) throws Exception;
    void updateNotice(NoticeVO noticeVO) throws Exception;
    void deleteNotice(int noticeId) throws Exception;
    void increaseViewCount(int noticeId) throws Exception; // 조회수 증가

    // ⭐⭐ 추가해야 할 메서드 ⭐⭐
    int getTotalCount() throws Exception; // 전체 공지사항 개수
    List<NoticeVO> getNoticesByPage(int offset, int limit) throws Exception; // 페이지별 목록 조회
}
