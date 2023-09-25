package springbootdeveloper.dto;

import lombok.Getter;
import springbootdeveloper.domain.Article_lombok;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;

    public ArticleResponse(Article_lombok article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
