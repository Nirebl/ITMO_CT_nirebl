package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class UserRepositoryImpl
        extends BaseDBRepository<User>
        implements UserRepository {

    private static User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public User find(long id) {
        return executeOneResult(
                "SELECT * FROM User WHERE id=?"
                , new Object[]{id}
                , UserRepositoryImpl::toUser);
    }

    @Override
    public User findByLogin(String login) {
        return executeOneResult(
                "SELECT * FROM User WHERE login=?"
                , new Object[]{login}
                , UserRepositoryImpl::toUser);
    }

    @Override
    public User findByEmail(String email) {
        return executeOneResult(
                "SELECT * FROM User WHERE email=?"
                , new Object[]{email}
                , UserRepositoryImpl::toUser);
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return executeOneResult(
                "SELECT * FROM User WHERE login=? AND passwordSha=?"
                , new Object[]{login, passwordSha}
                , UserRepositoryImpl::toUser);
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return executeOneResult(
                "SELECT * FROM User WHERE email=? AND passwordSha=?"
                , new Object[]{email, passwordSha}
                , UserRepositoryImpl::toUser);
    }

    @Override
    public List<User> findAll() {
        return executeManyResult(
                "SELECT * FROM User ORDER BY id DESC"
                , null
                , UserRepositoryImpl::toUser);
    }

    private static Long toLong(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        return resultSet.getLong(1);
    }

    @Override
    public long getNumberOfUsers() {
        return new BaseDBRepository<Long>().executeOneResult(
                "SELECT COUNT(*) FROM User"
                , null
                , UserRepositoryImpl::toLong);

//        return findAll().size();
    }

    @Override
    public void save(User user, String passwordSha) {
        long id = executeUpdate(
                "INSERT INTO `User` (`login`, `passwordSha`, `creationTime`, `email`) VALUES (?, ?, NOW(), ?)"
                , new Object[]{user.getLogin()
                        , passwordSha
                        , user.getEmail()
                }
        );

        user.setId(id);
        user.setCreationTime(find(id).getCreationTime());
    }
}
