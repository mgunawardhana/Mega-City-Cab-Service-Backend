package com.megacity.backend.website_management.service;


import com.megacity.backend.domain.entity.Article;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface WebContentManagementService {

    /**
     * Creates a new article.
     *
     * @param article the article to be created
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> createArticle(Article article);

    /**
     * Updates an existing article.
     *
     * @param article the article to be updated
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> updateArticle(Article article);

    /**
     * Deletes an article by its ID.
     *
     * @param articleId the ID of the article to be deleted
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> deleteArticle(Integer articleId);

    /**
     * Retrieves an article by its ID.
     *
     * @param articleId the ID of the article to be retrieved
     * @return a ResponseEntity containing the APIResponse with the article details
     */
    ResponseEntity<APIResponse> getArticle(Integer articleId);

    /**
     * Retrieves a paginated list of articles.
     *
     * @param page the page number to retrieve
     * @param size the number of articles per page
     * @return a ResponseEntity containing the APIResponse with the list of articles
     */
    ResponseEntity<APIResponse> getArticles(int page, int size);
}
