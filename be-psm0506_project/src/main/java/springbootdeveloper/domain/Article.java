package springbootdeveloper.domain;

import lombok.Builder;
import javax.persistence.*;

@Entity // 엔티티로 지정
public class Article {

    @Id // id 필드를 기본티로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // title이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder // 빌더 패턴으로 객체 생성(객체를 유연하고 직관적으로 생성할 수 있는 디자인 패턴)
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 빌더 패턴 사용전
    // new Article("abc", "def");
    //빌더 패턴 사용후
    // Article.builder()
    //   .title("abc")
    //   .content("def")
    //   .build();

    protected Article() { // 기본 생성자

    }

    //게터
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
