package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.repository.ArticleRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository {
    private static Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return article;
    }

    private Article find(long id) {
        return new BaseDBRepository<Article>().executeOneResult(
                "SELECT * FROM Article WHERE id=?"
                , new Object[]{id}
                , ArticleRepositoryImpl::toArticle);
    }

    @Override
    public void save(Article article) {
        long id = new BaseDBRepository<Article>().executeAdd(
                "INSERT INTO `Article` (`userId`, `title`, `text`, `creationTime`) VALUES (?, ?, ?, NOW())"
                , new Object[]{article.getUserId()
                        , article.getTitle()
                        , article.getText()
                }
        );

        article.setId(id);
        article.setCreationTime(find(id).getCreationTime());
    }

    @Override
    public void updateHidden(long id, boolean hidden) {
        new BaseDBRepository<Article>().executeUpdate(
                "UPDATE `Article` SET `hidden`=? WHERE id=?"
                , new Object[]{hidden
                        , id
                }
        );
    }

    @Override
    public List<Article> getAllVisible() {
        return new BaseDBRepository<Article>().executeManyResult(
                "SELECT * FROM Article Where hidden=false ORDER BY id DESC"
                , null
                , ArticleRepositoryImpl::toArticle);
    }

    @Override
    public List<Article> getUserArticles(long userId) {
        return new BaseDBRepository<Article>().executeManyResult(
                "SELECT * FROM Article Where userId=? ORDER BY id DESC"
                , new Object[]{userId}
                , ArticleRepositoryImpl::toArticle);
    }

}
