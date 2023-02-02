package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.TalksRepository;

import java.sql.*;
import java.util.List;

public class TalksRepositoryImpl
        implements TalksRepository {

    private static Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return talk;
    }

    private Talk find(long id) {
        return new BaseDBRepository<Talk>().executeOneResult(
                "SELECT * FROM Talk WHERE id=?"
                , new Object[]{id}
                , TalksRepositoryImpl::toTalk);
    }

    @Override
    public List<Talk> getUserTalks(long userId) {
        return new BaseDBRepository<Talk>().executeManyResult(
                "SELECT * FROM `Talk` WHERE sourceUserId = ? or targetUserId = ? ORDER BY id DESC"
                , new Object[]{userId, userId}
                , TalksRepositoryImpl::toTalk);
    }

    @Override
    public void save(Talk talk) {
        long id = new BaseDBRepository<Talk>().executeUpdate(
                "INSERT INTO `Talk` (`sourceUserId`, `targetUserId`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())"
                , new Object[]{talk.getSourceUserId()
                        , talk.getTargetUserId()
                        , talk.getText()
                }
        );

        talk.setId(id);
        talk.setCreationTime(find(id).getCreationTime());
    }
}
