package com.demidrolll.pet.bank.gateway.web.controller;

import com.demidrolll.pet.bank.gateway.web.model.Article;
import java.util.List;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ArticlesController {

  @QueryMapping("getArticles")
  public List<Article> getArticles() {
    return List.of(new Article("article 1"), new Article("article 2"), new Article("article 3"));
  }
}
