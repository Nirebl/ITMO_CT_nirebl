package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalksService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final TalksService _talksService = new TalksService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

        if (getUser() == null) {
            setMessage("Please log in");
            throw new RedirectException("/enter");
        }
    }

    @Override
    protected void after(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        super.after(request, view);

        view.put("users", super.getUserService().findAll());
        view.put("talks", _talksService.getUserTalks(getUser()));
    }

    protected void add_message(HttpServletRequest request, Map<String, Object> view) {
        try {
            Talk talk = new Talk();
            talk.setSourceUserId(getUser().getId());
            User targetUser = getUserService().findByLogin(request.getParameter("select"));
            talk.setTargetUserId(targetUser.getId());
            String text = request.getParameter("text");
            talk.setText(text);

            _talksService.sendTalk(talk);
        } catch (ValidationException e) {
            setMessage("Error send message: " + e.getMessage());
            throw new RedirectException("/talks");
        }


        setMessage("Your message send successfully");

        throw new RedirectException("/talks");
    }
}

