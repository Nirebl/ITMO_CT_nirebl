package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    private HttpServletRequest _request;
    private final UserService _userService = new UserService();
    private final EventService _eventService = new EventService();

    protected UserService getUserService() {
        return _userService;
    }
    protected EventService getEventService() {
        return _eventService;
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        this._request = request;

        putUser(view);
        putNumberOfUsers(view);
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        // No operations.
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        putMessage(view);
    }

    private void putUser(Map<String, Object> view) {
        User user = (User) _request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    protected void setUser(User user) throws ValidationException {
        _request.getSession().setAttribute("user", user);
        _eventService.onLoginUser(user);
    }

    protected void removeUser() throws ValidationException {
        _eventService.onLogoutUser(getUser());
        _request.getSession().removeAttribute("user");
    }

    protected User getUser() {
        return (User) _request.getSession().getAttribute("user");
    }

    protected void setMessage(String message) {
        _request.getSession().setAttribute("message", message);
    }

    private void putMessage(Map<String, Object> view) {
        String message = (String) _request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            _request.getSession().removeAttribute("message");
        }
    }

    private void putNumberOfUsers(Map<String, Object> view) {
        view.put("numberOfUsers", _userService.getNumberOfUsers());
    }

}
