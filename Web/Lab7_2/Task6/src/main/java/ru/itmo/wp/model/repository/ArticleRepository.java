package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);

    void updateHidden(long id, boolean hidden);

    List<Article> getAllVisible();

    List<Article> getUserArticles(long userId);
}
