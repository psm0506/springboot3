package springbootdeveloper.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // 엔티티로 지정
@Getter // 모든 필드에 대한 접근자 메서드를 만들 수 있음
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 접근 제어자가 protected인 기본 생성자를 생성
//public으로 할 경우 무분별한 객체 생성이 이루어질 가능성이 있어 체크하기 위함임, 객체 생성시 null값도 들어갈수있어 안정성 떨어짐
@ToString // 출력시 toString() 메소드를 자동으로 생성(exclude = "pwd") , 객체가 가지고 있는 정보나 값들을 문자열로 만들어 리턴하는 메소스
//@Table(name="테이블명") // 아래 클래스명과 다른이름으로 테이블명 지정시 사용
public class Article_lombok {

    @Id // id 필드를 기본티로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // title이라는 not null 컬럼과 매핑(자동 생성되는 DDL에 제약조건을 추가 할수 있음)
    @NotNull // DB와 서버 안정성으로 추천
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at") // 엔티티가 생성될 때 생성시간 지정
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at") // 엔티티가 수정될 때 수정시간 지정
    private LocalDateTime updatedAt;

//    @CreationTimestamp
//    @UpdateTimestamp
//    @OneToMany
//    @ManyToOne
//    @JoinColumn(name = "")
//    @Column(name = "test", nullable = false)
//    private String test;

    //아래와 깉이 빌더는 필요에 맞게 다양하게 만들수 있음
    @Builder // 빌더 패턴으로 객체 생성
    public Article_lombok(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    @Builder // 빌더 패턴으로 객체 생성
//    public Article_lombok(String content) {
//        this.title = "title_name";
//        this.content = content;
//    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}


