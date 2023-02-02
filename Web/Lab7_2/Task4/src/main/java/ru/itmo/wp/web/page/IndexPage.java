package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage extends Page{

    ArticleService _articleService = new ArticleService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
    }

    protected void findAllVisible(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", _articleService.getAllVisible());
        setMessage("Articles are downloaded");
    }


}
