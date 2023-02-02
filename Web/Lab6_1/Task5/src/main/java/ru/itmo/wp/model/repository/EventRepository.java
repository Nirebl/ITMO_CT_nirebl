package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Event;

import java.util.List;

public interface EventRepository {

    List<Event> getAll();

    List<Event> getUserEvents(long userId);

    void save(Event event);
}
