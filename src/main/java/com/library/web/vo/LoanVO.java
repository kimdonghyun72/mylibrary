package com.library.web.vo;

import java.time.LocalDateTime;

public class LoanVO {
    private int loanId;             // 대출 ID (PK)
    private String memberId;        // 회원 ID (FK)
    private int bookId;             // 도서 ID (FK)
    private LocalDateTime loanDate; // 대출일
    private LocalDateTime dueDate;  // 반납 예정일
    private LocalDateTime returnDate; // 실제 반납일 (반납 전에는 null)

    // 편의를 위해, LoanVO에 직접 도서 정보를 포함시킬 수도 있습니다.
    private String bookTitle;       // 도서 제목
    private String author;          // 도서 저자

    // 기본 생성자
    public LoanVO() {}

    // 모든 필드를 포함하는 생성자 (선택 사항)
    public LoanVO(int loanId, String memberId, int bookId, LocalDateTime loanDate,
                  LocalDateTime dueDate, LocalDateTime returnDate, String bookTitle, String author) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.bookTitle = bookTitle;
        this.author = author;
    }

    // Getter와 Setter 메서드 (모든 필드에 대해 생성)
    // 이클립스에서 마우스 우클릭 -> Source -> Generate Getters and Setters... 로 쉽게 생성 가능

    public int getLoanId() { return loanId; }
    public void setLoanId(int loanId) { this.loanId = loanId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public LocalDateTime getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDateTime loanDate) { this.loanDate = loanDate; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    @Override
    public String toString() {
        return "LoanVO{" +
               "loanId=" + loanId +
               ", memberId='" + memberId + '\'' +
               ", bookId=" + bookId +
               ", loanDate=" + loanDate +
               ", dueDate=" + dueDate +
               ", returnDate=" + returnDate +
               ", bookTitle='" + bookTitle + '\'' +
               ", author='" + author + '\'' +
               '}';
    }
}