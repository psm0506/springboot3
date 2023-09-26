package springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleafExample(Model model) { // 뷰로 데이터를 넘겨주는 모델 객체
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동","독서"));

        model.addAttribute("person",examplePerson); // Person 객체 저장
        model.addAttribute("today", LocalDate.now());

        return "example"; // example.html 이라는 뷰 조회
    }

    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }

//    @GetMapping("/thymeleaf/exampleVue")
//    public String thymeleafExampleVue(Model model) {
//        Person examplePerson2 = new Person();
//        examplePerson2.setId(1L);
//        examplePerson2.setName("홍길동");
//        examplePerson2.setAge(11);
//        examplePerson2.setHobbies(List.of("운동","독서"));
//
//        model.addAttribute("person",examplePerson2);
//        model.addAttribute("today", LocalDate.now());
//
//        return "exampleVue";
//    }
}

