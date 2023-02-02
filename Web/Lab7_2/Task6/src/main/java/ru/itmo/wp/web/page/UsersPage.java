package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", getUserService().findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        try {
            view.put("user",
                    getUserService().find(Long.parseLong(request.getParameter("userId"))));
        } catch (NumberFormatException ex) {
            throw new ValidationException("Incorrect userId", ex);
        }
    }

    private void updateAdminUser(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User currUser = getUser();
        if (getUser() == null) {
            setMessage("Please log in");
            throw new RedirectException("/enter");
        }

        try {
            long updatedUserid = Long.parseLong(request.getParameter("updatedUserid"));
            boolean setRule = Boolean.parseBoolean(request.getParameter("rule"));

            getUserService().updateRule(getUser().getId(), updatedUserid, setRule);

            if(currUser.getId() == updatedUserid)
                setUser(getUserService().find(updatedUserid));
        } catch (NumberFormatException ex) {
            throw new ValidationException("Incorrect userId", ex);
        }
    }

}
