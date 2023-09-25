package springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootdeveloper.domain.Article_lombok;

public interface BlogRepository extends JpaRepository<Article_lombok, Long> {
    // JpaRepository 클래스를 상속받을 때 엔티티 Article_lombok과 엔티티의 PK 타입 Long을 인수로 넣음
}
