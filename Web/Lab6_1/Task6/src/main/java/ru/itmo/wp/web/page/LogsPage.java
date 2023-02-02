package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused"})
public class LogsPage extends Page{

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", super.getUserService().findAll());
        view.put("events", super.getEventService().getAll());
    }
}
