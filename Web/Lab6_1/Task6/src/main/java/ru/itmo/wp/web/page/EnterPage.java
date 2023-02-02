package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class EnterPage extends Page {

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        try {
            getUserService().validateEnter(loginOrEmail, password);
        } catch (ValidationException e) {
            setMessage(e.getMessage());
            throw new RedirectException("/enter");
        }

        User user = super.getUserService().findByLoginOrEmailAndPassword(loginOrEmail, password);
        setUser(user);
        setMessage("Hello");

        throw new RedirectException("/index");
    }
}
