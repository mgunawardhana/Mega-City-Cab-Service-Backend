package com.megacity.backend.website_management.service.impl;


import com.megacity.backend.constant.SqlQuery;
import com.megacity.backend.domain.entity.Article;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.website_management.service.WebContentManagementService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Service
public class WebContentManagementServiceImpl implements WebContentManagementService {

    @NonNull
    private final JdbcTemplate writeJdbcTemplate;

    @NonNull
    private final JdbcTemplate readJdbcTemplate;

    @NonNull
    private final ResponseUtil responseUtil;

    public WebContentManagementServiceImpl(@NonNull JdbcTemplate writeJdbcTemplate, @NonNull JdbcTemplate readJdbcTemplate, @NonNull ResponseUtil responseUtil) {
        this.writeJdbcTemplate = writeJdbcTemplate;
        this.readJdbcTemplate = readJdbcTemplate;
        this.responseUtil = responseUtil;
    }

    @Override
    public ResponseEntity<APIResponse> createArticle(Article article) {
        try {
            writeJdbcTemplate.update(
                    SqlQuery.InsertQuery.INSERT_ARTICLE,
                    article.getRatings(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getAuthor(),
                    article.getMedia(),
                    article.getIs_active());

            return responseUtil.wrapSuccess("Article created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error during article creation: ", e);
            return responseUtil.wrapError("Failed to create article", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> updateArticle(Article article) {
        try {
            writeJdbcTemplate.update(
                    SqlQuery.UpdateQuery.UPDATE_ARTICLE,
                    article.getRatings(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getAuthor(),
                    article.getMedia(),
                    article.getIs_active(),
                    article.getArticleId());

            return responseUtil.wrapSuccess("Article updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating article: ", e);
            return responseUtil.wrapError("Failed to update article", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> deleteArticle(Integer articleId) {
        try {
            writeJdbcTemplate.update(SqlQuery.DeleteQuery.DELETE_ARTICLE, articleId);
            return responseUtil.wrapSuccess("Article deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting article: ", e);
            return responseUtil.wrapError("Failed to delete article", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getArticleById(Integer articleId) {
        try {
            Article article = readJdbcTemplate.queryForObject(
                    SqlQuery.SelectQuery.SELECT_ARTICLE_BY_ID,
                    new Object[]{articleId},
                    (rs, rowNum) ->
                            Article.builder()
                                    .articleId(rs.getInt("article_id"))
                                    .ratings(rs.getDouble("discount"))
                                    .title(rs.getString("title"))
                                    .description(rs.getString("description"))
                                    .author(rs.getString("author"))
                                    .media(rs.getString("media"))
                                    .is_active(rs.getBoolean("is_active"))
                                    .build());
            return responseUtil.wrapSuccess(article, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            log.error("Article not found with ID: {}", articleId, e);
            return responseUtil.wrapError("Article not found", "Not Found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error fetching article by ID: ", e);
            return responseUtil.wrapError("Failed to fetch article", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<APIResponse> getArticles(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "4") int size) {
        try {
            List<Article> articles = readJdbcTemplate.query(
                    SqlQuery.SelectQuery.SELECT_ARTICLES,
                    new Object[]{size, page * size},
                    (rs, rowNum) ->
                            Article.builder()
                                    .articleId(rs.getInt("article_id"))
                                    .ratings(rs.getDouble("discount"))
                                    .title(rs.getString("title"))
                                    .description(rs.getString("description"))
                                    .author(rs.getString("author"))
                                    .media(rs.getString("media"))
                                    .is_active(rs.getBoolean("is_active"))
                                    .build());
            return responseUtil.wrapSuccess(articles, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching articles: ", e);
            return responseUtil.wrapError("Failed to fetch articles", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
