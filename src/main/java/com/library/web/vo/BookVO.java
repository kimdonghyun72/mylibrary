package com.library.web.vo;

import java.time.LocalDateTime;

public class BookVO {
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private LocalDateTime publicationDate; // 출판일
    private String isbn;
    private String genre;
    private String description;
    private int totalCount;    // 전체 재고 수
    private int currentCount;  // 현재 대출 가능한 수
    private LocalDateTime regDate;     // 책 등록일 (신작/추천작 정렬 기준)
    // private String coverImage; // 책 표지 이미지 경로 (선택 사항, 필요 시 추가)

    // 기본 생성자
    public BookVO() {}

    // 모든 필드를 포함하는 생성자 (선택 사항)
    public BookVO(int bookId, String title, String author, String publisher, LocalDateTime publicationDate,
                  String isbn, String genre, String description, int totalCount, int currentCount, LocalDateTime regDate) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.genre = genre;
        this.description = description;
        this.totalCount = totalCount;
        this.currentCount = currentCount;
        this.regDate = regDate;
    }

    // Getter와 Setter 메서드
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public LocalDateTime getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDateTime publicationDate) { this.publicationDate = publicationDate; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getTotalCount() { return totalCount; }
    public void setTotalCount(int totalCount) { this.totalCount = totalCount; }
    public int getCurrentCount() { return currentCount; }
    public void setCurrentCount(int currentCount) { this.currentCount = currentCount; }
    public LocalDateTime getRegDate() { return regDate; }
    public void setRegDate(LocalDateTime regDate) { this.regDate = regDate; }
    // public String getCoverImage() { return coverImage; } // 필요 시 주석 해제
    // public void setCoverImage(String coverImage) { this.coverImage = coverImage; } // 필요 시 주석 해제

    @Override
    public String toString() {
        return "BookVO{" +
               "bookId=" + bookId +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", publisher='" + publisher + '\'' +
               ", publicationDate=" + publicationDate +
               ", isbn='" + isbn + '\'' +
               ", genre='" + genre + '\'' +
               ", description='" + description + '\'' +
               ", totalCount=" + totalCount +
               ", currentCount=" + currentCount +
               ", regDate=" + regDate +
               '}';
    }
}