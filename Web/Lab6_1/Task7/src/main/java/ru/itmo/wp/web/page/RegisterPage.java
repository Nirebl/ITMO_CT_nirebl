package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class RegisterPage extends Page{

    private void register(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        try {
            super.getUserService().validateRegistration(user, password, passwordConfirmation);
        }
        catch (ValidationException e) {
            setMessage(e.getMessage());
            throw new RedirectException("/register");
        }
        super.getUserService().register(user, password);

        setMessage("You are successfully registered!");

        throw new RedirectException("/enter");
    }
}
