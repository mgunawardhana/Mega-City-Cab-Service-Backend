package com.megacity.backend.website_management.service;


import com.megacity.backend.domain.entity.Article;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface WebContentManagementService {

    /**
     * Creates a new article in the system.
     *
     * @param article the {@link Article} object containing the article details to be created
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the creation operation
     */
    ResponseEntity<APIResponse> createArticle(Article article);

    /**
     * Updates an existing article in the system.
     *
     * @param article the {@link Article} object containing the updated article details
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the update operation
     */
    ResponseEntity<APIResponse> updateArticle(Article article);

    /**
     * Deletes an article from the system based on its ID.
     *
     * @param articleId the unique identifier of the article to be deleted
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the deletion operation
     */
    ResponseEntity<APIResponse> deleteArticle(Integer articleId);

    /**
     * Retrieves a specific article by its ID.
     *
     * @param articleId the unique identifier of the article to fetch
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the requested article details
     */
    ResponseEntity<APIResponse> getArticleById(Integer articleId);

    /**
     * Retrieves a paginated list of all articles.
     *
     * @param page the page number to retrieve (zero-based)
     * @param size the number of articles per page
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the list of articles
     */
    ResponseEntity<APIResponse> getArticles(int page, int size);
}
