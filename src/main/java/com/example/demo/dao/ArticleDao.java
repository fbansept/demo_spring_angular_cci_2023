package com.example.demo.dao;

import com.example.demo.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleDao extends JpaRepository<Article, Integer> {
    Optional<Article> findByTitre(String titre);
}
