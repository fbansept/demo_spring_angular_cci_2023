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
    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable int id) {

        Optional<Article> articleBdd = articleDao.findById(id);

        if(articleBdd.isPresent()) {
            return new ResponseEntity<>(articleBdd.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/article")
    public ResponseEntity<Article> editionArticle(@RequestBody Article article) {



        if(article.getId() == null) {

            Optional<Article> articleBdd = articleDao.findByTitre(article.getTitre());

            //Un article porte déjà ce nom
            if (articleBdd.isPresent()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            articleDao.save(article);

            return new ResponseEntity<>(article, HttpStatus.CREATED);
        }

        //si c'est une modification

        Optional<Article> articleBdd = articleDao.findById(article.getId());

        if(articleBdd.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<Article> articleDoublon = articleDao.findByTitreAndIdNotEqual(
                article.getTitre(),
                article.getId()
        );

        if(articleDoublon.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        articleDao.save(article);
        return new ResponseEntity<>(article, HttpStatus.OK);

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
