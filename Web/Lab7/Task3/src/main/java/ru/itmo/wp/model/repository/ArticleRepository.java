package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

public interface ArticleRepository {
    void save(Article article);
}
