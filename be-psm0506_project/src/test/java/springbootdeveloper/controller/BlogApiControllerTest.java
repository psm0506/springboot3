package springbootdeveloper.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springbootdeveloper.domain.Article_builder;
import springbootdeveloper.domain.Article_data;
import springbootdeveloper.domain.Article_lombok;
import springbootdeveloper.dto.AddArticleRequest;
import springbootdeveloper.dto.UpdateArticleRequest;
import springbootdeveloper.repository.BlogRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트(컴포넌트 스캔 범위가 빈 전체여서 실제와 유사한 환경에서 테스트 가능하나
// 범위가 넓을수록 테스트가 느려서 단위테스트에 적절하지 않다.
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성, @WebMvcTest도 비슷하게 사용 가능
// 서블릿 컨테이너가 모킹됨
// Mock : 테스트를 위해 만든 모형
// WebMvcTest와 차이점은 컨트롤러 뿐만 아니라 서비스, 리파지토리 어노테이션이 붙은 객체들도 모두 메모리에 올린다
// 단위 테스트에 적합하지 않다
public class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc; // 웹 API테스트할 때 사용

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역질렬화를 위한 클래스

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetup() {
        System.out.println("@@@@@BeforeEach" );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("addArticle : 블로그 글 추가에 성공한다.")
    @Test
    public void addArticle() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";
        final AddArticleRequest userRequest = new AddArticleRequest(title, content);

        // 객체를 JSON으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        // when
        // 설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated()); //201
//        result.andExpect(view().name("hello")); //컨트롤러가 리턴하는 뷰 검증
//        result.andExpect(redirectedUrl("/index")); // index화면으로 리다이렉트했는지 검
//        result.andExpect(model().attributeExists("test")); //test에 해당하는 데이터가 Model에 포함되어 있는지 검증
//        result.andExpect(model().attributeExists("test","value")); //test에 해당하는 데이터가 value 객체인지 검증

        List<Article_lombok> articles = blogRepository.findAll();

        // AssertJ 라이브러리 사용
        assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
        assertThat(articles.get(0).getTitle()).isEqualTo(title);
        assertThat(articles.get(0).getContent()).isEqualTo(content);

        System.out.println("제목 = " + articles.get(0).getTitle());
        System.out.println("내용 = " + articles.get(0).getContent());

        // 생성자 레벨 빌더 사용
        Article_lombok article_lombok = Article_lombok.builder()
                .title("title")
                .content("content")
                .build();
        System.out.println("article_lombok = " + article_lombok);

        // 제목이 없을 경우 기본값 출력
        Article_lombok article_lombok1 = Article_lombok.builder()
                .content("content")
                .build();
        System.out.println("article_lombok1 = " + article_lombok1);

        // 클래스 레벨 빌더 사용
        Article_builder article_builder = Article_builder.builder()
                .title("title")
                .content("content")
                .build();
        System.out.println("article_builder = " + article_builder);

        // data 어노테이션 사용
        Article_data article_data = Article_data.builder()
                .title("title")
                .content("content")
                .build();

        System.out.println("article_data = " + article_data);

    }

    @DisplayName("findAllArticle : 블로그 글 목록 조회에 성공한다.")
    @Test
    public void findAllArticles() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "title";
        final String content = "content";

       blogRepository.save(Article_lombok.builder()
               .title(title)
               .content(content)
               .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk()) //200
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findAllArticle : 블로그 글 조회에 성공한다.")
    @Test
    public void findArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article_lombok savedArticle = blogRepository.save(Article_lombok.builder()
                .title(title)
                .content(content)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

        // then
        resultActions
                .andExpect(status().isOk()) //200
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("findAllArticle : 블로그 글 삭제에 성공한다.")
    @Test
    public void deleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article_lombok savedArticle = blogRepository.save(Article_lombok.builder()
                .title(title)
                .content(content)
                .build());

        // when
        mockMvc.perform(delete(url, savedArticle.getId()));

        // then
        List<Article_lombok> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

    @DisplayName("findAllArticle : 블로그 글 수정에 성공한다.")
    @Test
    public void updateArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        final String title = "title";
        final String content = "content";

        Article_lombok savedArticle = blogRepository.save(Article_lombok.builder()
                .title(title)
                .content(content)
                .build());

        final String newTitle = "new title";
        final String newContent = "new content";

        UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

        // when
        ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk());

        Article_lombok article = blogRepository.findById(savedArticle.getId()).get();

        assertThat(article.getTitle()).isEqualTo(newTitle);
        assertThat(article.getContent()).isEqualTo(newContent);
    }
}