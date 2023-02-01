package com.example.demo.controllers;

import com.example.demo.dao.ArticleDao;
import com.example.demo.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleDao articleDao;

    @GetMapping("/liste-article")
    public List<Article> getListeArticle() {

        return articleDao.findAll();

    }

    @PostMapping("/article")
    public ResponseEntity<Article> ajoutArticle(@RequestBody Article article) {

        Optional<Article> articleBdd = articleDao.findByTitre(article.getTitre());

        //Un article porte déjà ce nom
        if(articleBdd.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        articleDao.save(article);
        return new ResponseEntity<>(article,HttpStatus.CREATED);

    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Article> suppressionArticle(@PathVariable int id) {

        Optional<Article> articleBdd = articleDao.findById(id);

        if(articleBdd.isPresent()) {
            articleDao.deleteById(id);
            return new ResponseEntity<>(articleBdd.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
