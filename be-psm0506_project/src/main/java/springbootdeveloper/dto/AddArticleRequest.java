package springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Description;
import springbootdeveloper.domain.Article_lombok;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Description("Article 등록용 request")
public class AddArticleRequest {
    private String title;
    private String content;

    // toEntity() : 빌더 패턴을 사용해 DTO를 엔티티로 만들어 주는 메서드
    public Article_lombok toEntity() { // 생성자를 사용해 객체 생성
        return Article_lombok.builder()
                .title(title)
                .content(content)
                .build();
    }
}
