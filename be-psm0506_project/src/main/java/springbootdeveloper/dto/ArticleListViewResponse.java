package springbootdeveloper.dto;

import lombok.Getter;
import springbootdeveloper.domain.Article;
import springbootdeveloper.domain.Article_lombok;

@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article_lombok article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
