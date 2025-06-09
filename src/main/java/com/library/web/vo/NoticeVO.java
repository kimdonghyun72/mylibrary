// com.library.web.vo.NoticeVO.java 예시
package com.library.web.vo;

import java.time.LocalDateTime; // LocalDateTime 임포트

public class NoticeVO {
    private int noticeId;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private int viewCount;
    private LocalDateTime modDate; // mod_date 추가

    // 기본 생성자 (필수)
    public NoticeVO() {}

    // 모든 필드를 포함하는 생성자 (선택 사항, 있으면 편리)
    public NoticeVO(int noticeId, String title, String content, LocalDateTime regDate, int viewCount, LocalDateTime modDate) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.viewCount = viewCount;
        this.modDate = modDate;
    }

    // Getter와 Setter 메서드 (모든 필드에 대해 존재해야 함)
    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    // --- modDate Getter/Setter 추가 확인 ---
    public LocalDateTime getModDate() {
        return modDate;
    }

    public void setModDate(LocalDateTime modDate) {
        this.modDate = modDate;
    }
    // ------------------------------------

    @Override
    public String toString() {
        return "NoticeVO{" +
                "noticeId=" + noticeId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regDate=" + regDate +
                ", viewCount=" + viewCount +
                ", modDate=" + modDate +
                '}';
    }
}