package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page{

    ArticleService _articleService = new ArticleService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            setMessage("Please log in");
            throw new RedirectException("/enter");
        }
    }


    protected void submit(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        Article article = new Article();
        article.setUserId(getUser().getId());
        article.setTitle(title);
        article.setText(text);

        _articleService.save(article);

        setMessage("Your article titled: " + article.getTitle() + " is saved");
    }

}
