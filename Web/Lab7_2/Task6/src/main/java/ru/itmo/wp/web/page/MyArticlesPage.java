package ru.itmo.wp.web.page;

import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {

    ArticleService _articleService = new ArticleService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            setMessage("Please log in");
            throw new RedirectException("/enter");
        }
    }

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        view.put("articles", _articleService.getUserArticles(getUser().getId()));
        setMessage("Articles are downloaded");
    }

    protected void updateHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long articleId = Long.parseLong(request.getParameter("articleId"));
        boolean hidden = Boolean.parseBoolean(request.getParameter("hidden"));

        if (hidden)
            _articleService.hideArticle(articleId);
        else
            _articleService.showArticle(articleId);

        setMessage("Article is changed");
    }
}
