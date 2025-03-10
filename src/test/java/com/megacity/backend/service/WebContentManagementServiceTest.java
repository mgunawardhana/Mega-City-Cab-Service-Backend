package com.megacity.backend.service;

import com.megacity.backend.domain.entity.Article;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.website_management.service.impl.WebContentManagementServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class WebContentManagementServiceTest {

    private final ResponseUtil responseUtils = mock(ResponseUtil.class);
    private final JdbcTemplate writeJdbcTemplate = mock(JdbcTemplate.class);
    private final JdbcTemplate readJdbcTemplate = mock(JdbcTemplate.class);

    private final WebContentManagementServiceImpl webContentManagementService = new WebContentManagementServiceImpl(writeJdbcTemplate, readJdbcTemplate, responseUtils);

    @Test
    @DisplayName("create article success scenario")
    void createArticle() {
        Article article = new Article();
        article.setRatings(5);
        article.setTitle("Test Article");
        article.setDescription("Test Description");
        article.setAuthor("Test Author");
        article.setMedia("Test Media");
        article.setIs_active(true);
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.CREATED))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.CREATED));
        ResponseEntity<APIResponse> response = webContentManagementService.createArticle(article);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    @DisplayName("create article error scenario")
    void createArticleError() {
        Article article = new Article();
        article.setRatings(5);
        article.setTitle("Test Article");
        article.setDescription("Test Description");
        article.setAuthor("Test Author");
        article.setMedia("Test Media");
        article.setIs_active(true);
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = webContentManagementService.createArticle(article);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("update article success scenario")
    void updateArticle() {
        Article article = new Article();
        article.setRatings(5);
        article.setTitle("Test Article");
        article.setDescription("Test Description");
        article.setAuthor("Test Author");
        article.setMedia("Test Media");
        article.setIs_active(true);
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = webContentManagementService.updateArticle(article);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("update article error scenario")
    void updateArticleError() {
        Article article = new Article();
        article.setRatings(5);
        article.setTitle("Test Article");
        article.setDescription("Test Description");
        article.setAuthor("Test Author");
        article.setMedia("Test Media");
        article.setIs_active(true);
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = webContentManagementService.updateArticle(article);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("delete article success scenario")
    void deleteArticle() {
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        when(responseUtils.wrapSuccess(anyString(), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = webContentManagementService.deleteArticle(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("delete article error scenario")
    void deleteArticleError() {
        when(writeJdbcTemplate.update(anyString(), any(Object[].class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = webContentManagementService.deleteArticle(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("get article success scenario")
    void getArticleById() {
        Article article = new Article();
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(article);
        when(responseUtils.wrapSuccess(any(Article.class), eq(HttpStatus.OK))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.OK));
        ResponseEntity<APIResponse> response = webContentManagementService.getArticleById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("get article error scenario")
    void getArticleByIdError() {
        when(readJdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class))).thenThrow(new RuntimeException("Database error"));
        when(responseUtils.wrapError(anyString(), anyString(), eq(HttpStatus.INTERNAL_SERVER_ERROR))).thenReturn(new ResponseEntity<>(APIResponse.builder().build(), HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseEntity<APIResponse> response = webContentManagementService.getArticleById(1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
