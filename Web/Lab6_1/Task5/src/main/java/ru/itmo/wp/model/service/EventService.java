package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

import java.util.List;

public class EventService {

    private final EventRepository eventRepository = new EventRepositoryImpl();
    private final UserService _userService = new UserService();

    protected void makeEvent(User user, Event.Type eventType) throws ValidationException {
        _userService.validateUser(user);

        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(eventType);

        eventRepository.save(event);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    public List<Event> getUserEvents(User user) {
        return eventRepository.getUserEvents(user.getId());
    }

    public void onLoginUser(User user) throws ValidationException {
        makeEvent(user, Event.Type.ENTER);
    }

    public void onLogoutUser(User user) throws ValidationException {
        makeEvent(user, Event.Type.LOGOUT);
    }

}
