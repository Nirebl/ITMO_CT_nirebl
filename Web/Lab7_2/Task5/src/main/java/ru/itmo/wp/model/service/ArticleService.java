package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository _articleRepository = new ArticleRepositoryImpl();
    private final UserService _userService = new UserService();

    private void validateArticle(Article article) throws ValidationException {
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

    public List<Article> getAllVisible() {
        return _articleRepository.getAllVisible();
    }

    public List<Article> getUserArticles(long userId) throws ValidationException {
        _userService.validateUserId(userId);

        return _articleRepository.getUserArticles(userId);
    }

    private void updateHidden(long articleId, boolean isHidden) {
        _articleRepository.updateHidden(articleId, isHidden);
    }

    public void showArticle(long articleId) {
        updateHidden(articleId, false);
    }

    public void hideArticle(long articleId) {
        updateHidden(articleId, true);
    }

}