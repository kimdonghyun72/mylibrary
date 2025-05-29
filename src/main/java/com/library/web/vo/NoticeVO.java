package com.library.web.vo;

import java.time.LocalDateTime;

public class NoticeVO {
    private int noticeId;           // 공지사항 ID (PK)
    private String title;           // 공지사항 제목
    private String content;         // 공지사항 내용
    private LocalDateTime regDate;  // 등록일
    private int views;              // 조회수 (선택 사항)

    // 기본 생성자
    public NoticeVO() {}

    // 모든 필드를 포함하는 생성자 (선택 사항)
    public NoticeVO(int noticeId, String title, String content, LocalDateTime regDate, int views) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.views = views;
    }

    // Getter와 Setter 메서드
    public int getNoticeId() { return noticeId; }
    public void setNoticeId(int noticeId) { this.noticeId = noticeId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getRegDate() { return regDate; }
    public void setRegDate(LocalDateTime regDate) { this.regDate = regDate; }
    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    @Override
    public String toString() {
        return "NoticeVO{" +
               "noticeId=" + noticeId +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", regDate=" + regDate +
               ", views=" + views +
               '}';
    }
} 