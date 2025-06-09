package com.library.web.vo;

// import java.time.LocalDateTime; // 이 라인은 이제 필요 없을 수도 있습니다.
// import org.springframework.format.annotation.DateTimeFormat; // 이 라인도 필요 없을 수도 있습니다.

public class BookVO {
    private int seq_no; // bookId -> seq_no
    private String title_nm; // title -> title_nm
    private String authr_nm; // author -> authr_nm
    private String publisher_nm; // publisher -> publisher_nm

    // 데이터베이스 'two_pblicte_de' 컬럼이 YYYY-MM-DD 형식의 문자열이므로 String으로 매핑
    private String two_pblicte_de; // publicationDate -> two_pblicte_de (String 타입)

    // DB 테이블에 없는 필드는 일단 제외하거나 주석 처리합니다.
    // private String isbn;
    // private String genre;

    private String book_intrcn_cn; // description -> book_intrcn_cn

    // DB 테이블에 없는 필드는 일단 제외하거나 주석 처리합니다.
    // private int totalCount;
    // private int currentCount;
    // private LocalDateTime regDate;

    private String cover_image; // coverImage -> cover_image (DB 컬럼명과 일치시키는 것이 좋음)

    // 기본 생성자
    public BookVO() {}

    // 필드를 포함하는 생성자 (수정된 필드명으로)
    public BookVO(int seq_no, String title_nm, String authr_nm, String publisher_nm,
                  String two_pblicte_de, String book_intrcn_cn, String cover_image) {
        this.seq_no = seq_no;
        this.title_nm = title_nm;
        this.authr_nm = authr_nm;
        this.publisher_nm = publisher_nm;
        this.two_pblicte_de = two_pblicte_de;
        this.book_intrcn_cn = book_intrcn_cn;
        this.cover_image = cover_image;
    }

    // Getter와 Setter 메서드 (수정된 필드명으로)
    public int getSeq_no() { return seq_no; }
    public void setSeq_no(int seq_no) { this.seq_no = seq_no; }

    public String getTitle_nm() { return title_nm; }
    public void setTitle_nm(String title_nm) { this.title_nm = title_nm; }

    public String getAuthr_nm() { return authr_nm; }
    public void setAuthr_nm(String authr_nm) { this.authr_nm = authr_nm; }

    public String getPublisher_nm() { return publisher_nm; }
    public void setPublisher_nm(String publisher_nm) { this.publisher_nm = publisher_nm; }

    public String getTwo_pblicte_de() { return two_pblicte_de; }
    public void setTwo_pblicte_de(String two_pblicte_de) { this.two_pblicte_de = two_pblicte_de; }

    public String getBook_intrcn_cn() { return book_intrcn_cn; }
    public void setBook_intrcn_cn(String book_intrcn_cn) { this.book_intrcn_cn = book_intrcn_cn; }

    public String getCover_image() { return cover_image; }
    public void setCover_image(String cover_image) { this.cover_image = cover_image; }

    // toString() 메서드도 수정된 필드명에 맞게 업데이트 해주세요.
    @Override
    public String toString() {
        return "BookVO{" +
                "seq_no=" + seq_no +
                ", title_nm='" + title_nm + '\'' +
                ", authr_nm='" + authr_nm + '\'' +
                ", publisher_nm='" + publisher_nm + '\'' +
                ", two_pblicte_de='" + two_pblicte_de + '\'' +
                ", book_intrcn_cn='" + book_intrcn_cn + '\'' +
                ", cover_image='" + cover_image + '\'' +
                '}';
    }
}