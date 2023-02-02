package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;
import java.util.List;

public class EventRepositoryImpl
        extends BaseDBRepository<Event>
        implements EventRepository {

    private static Event toEvent(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(Event.Type.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }

    protected Event find(long id) {
        return executeOneResult(
                "SELECT * FROM Event WHERE id=?"
                , new Object[]{id}
                , EventRepositoryImpl::toEvent);
    }

    @Override
    public List<Event> getAll() {
        return executeManyResult(
                "SELECT * FROM Event ORDER BY id DESC"
                , null
                , EventRepositoryImpl::toEvent);
    }

    @Override
    public List<Event> getUserEvents(long userId) {
        return executeManyResult(
                "SELECT * FROM Event Where userId=? ORDER BY id DESC"
                , new Object[]{userId}
                , EventRepositoryImpl::toEvent);
    }


    @Override
    public void save(Event event) {
        long id = executeUpdate(
                "INSERT INTO `Event` (`userId`, `type`, `creationTime`) VALUES (?, ?, NOW())"
                , new Object[]{event.getUserId(), event.getType().toString()}
        );

        event.setId(id);
        event.setCreationTime(find(id).getCreationTime());
    }
}
