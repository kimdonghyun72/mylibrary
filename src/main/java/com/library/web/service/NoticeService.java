package com.library.web.service;

import com.library.web.vo.NoticeVO; // NoticeVO 임포트

import java.util.List;

public interface NoticeService {
    // 기존 메서드 (추가/수정/삭제/상세조회)
    void registerNotice(NoticeVO noticeVO) throws Exception;
    NoticeVO getNoticeDetail(int noticeId) throws Exception; // 조회수 증가 포함
    void modifyNotice(NoticeVO noticeVO) throws Exception;
    void removeNotice(int noticeId) throws Exception;

    // ⭐⭐ 추가해야 할 메서드 ⭐⭐
    int getTotalNoticeCount() throws Exception; // 전체 공지사항 개수를 반환하는 메서드
    List<NoticeVO> getNoticesByPage(int offset, int limit) throws Exception; // 페이지별 공지사항 목록을 반환하는 메서드
}