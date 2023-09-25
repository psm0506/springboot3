package springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootdeveloper.domain.Article_lombok;
import springbootdeveloper.dto.AddArticleRequest;
import springbootdeveloper.dto.UpdateArticleRequest;
import springbootdeveloper.repository.BlogRepository;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가, @Autowired 사용하지 않고 의존성 주입
@Service // 해당 클래스를 빈으로 서블릿 컨테이너에 등록
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article_lombok save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity()); // AddArticleRequest 클래스에 저장된 값들을 article_lombok 데이터 베이스에 저장
    }

    // 블로그 모든 글 조회 메서드
    public List<Article_lombok> findAll() {
        return blogRepository.findAll();
    }

    // 블로그 글 조회 메서드
    public Article_lombok findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id)); // 없을 때 예외처리
    }

    // 블로그 글 삭제 메서드
    public void delete(long id) { blogRepository.deleteById(id); }

    // 블로그 글 수정 메서드
    @Transactional // 매칭한 매서드를 하나의 트랜잭션으로 묶는 역할
    // A계좌에서 출근 + B계좌에서 입금
    // 중간에 오류시 트랜잭션의 처음 상태로 되돌려줌
    public Article_lombok update(long id, UpdateArticleRequest request) {
       Article_lombok article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id)); // 없을 때 예외처리
        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
