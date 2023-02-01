package com.example.demo.controllers;

import com.example.demo.dao.ArticleDao;
import com.example.demo.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;

    @GetMapping("/liste-article")
    public List<Article> getListeArticle() {

        return articleDao.findAll();

    }
}
