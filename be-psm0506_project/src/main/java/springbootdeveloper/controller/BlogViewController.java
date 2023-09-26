package springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springbootdeveloper.domain.Article_lombok;
import springbootdeveloper.dto.ArticleListViewResponse;
import springbootdeveloper.dto.ArticleViewResponse;
import springbootdeveloper.service.BlogService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
    private final BlogService blogService;

    /**
     * 블로그 글 목록 조회
     *
     * @param model
     * @return
     */
    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = blogService.findAll().stream()
                .map(ArticleListViewResponse::new) //추가
                .toList();
        model.addAttribute("articles", articles); // 블로그 글 리스트 저장

        return "articleList"; // articleList.html 뷰 조회
    }

    /**
     * 블로그 글 조회
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/articles/{id}")
    public String getArticles(@PathVariable Long id, Model model) {
        Article_lombok article = blogService.findById(id);
        model.addAttribute("article", new ArticleViewResponse(article)); // 블로그 글 리스트 저장

        return "article"; // articleList.html 뷰 조회
    }

    /**
     * 블로그 글 수정/생성
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/new-article")
    // id 키를 가진 쿼리 파라미터의 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) { // 없으면 생성 화면으로 이동
            model.addAttribute("article", new ArticleViewResponse());
        } else { // 있으면 수정 화면으로 이동
            Article_lombok article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
        }

        return "newArticle";
    }
}
