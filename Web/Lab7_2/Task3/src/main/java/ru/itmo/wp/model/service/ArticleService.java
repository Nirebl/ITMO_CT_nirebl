package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

public class ArticleService {
    private final ArticleRepository _articleRepository = new ArticleRepositoryImpl();
    private final UserService _userService = new UserService();

    private void validateArticle(Article article) throws ValidationException
    {
        _userService.validateUserId(article.getUserId());
        if (Strings.isNullOrEmpty(article.getTitle()))
            throw new ValidationException("The title is empty");
        if (Strings.isNullOrEmpty(article.getText()))
            throw new ValidationException("The text is empty");
    }
    public void save(Article article) throws ValidationException {
        validateArticle(article);

        _articleRepository.save(article);
    }
}
