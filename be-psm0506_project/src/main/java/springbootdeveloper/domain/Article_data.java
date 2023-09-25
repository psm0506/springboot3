package springbootdeveloper.domain;

import lombok.*;

import javax.persistence.*;

@Entity // 엔티티로 지정
@Data // 지양필요, 자동으로 Setter 지원
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Article_data {

    @Id // id 필드를 기본티로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // title이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

}


