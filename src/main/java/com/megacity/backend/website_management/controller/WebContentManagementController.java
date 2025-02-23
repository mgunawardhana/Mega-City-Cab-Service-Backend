package com.megacity.backend.website_management.controller;


import com.megacity.backend.domain.entity.Article;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.util.ResponseUtil;
import com.megacity.backend.website_management.service.WebContentManagementService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/web-content")
@RequiredArgsConstructor
public class WebContentManagementController {

    @NonNull
    private final WebContentManagementService webContentManagementService;

    @NonNull
    private final ResponseUtil responseUtil;

    @GetMapping("/public")
    public ResponseEntity<APIResponse> checkingIfDeploymentIsSuccess() {
        log.info("Checking if deployment is successful");
        return responseUtil.wrapSuccess("powerhouse-backend is up and running on aws EC2 instance.", HttpStatus.CREATED);
    }

    @PostMapping("/createArticle")
    public ResponseEntity<APIResponse> createArticle(@RequestBody Article article) {
        log.info("Article: {}", article.toString());
        return webContentManagementService.createArticle(article);
    }

    @DeleteMapping("/deleteArticle/{articleId}")
    public ResponseEntity<APIResponse> deleteArticle(@PathVariable Integer articleId) {
        log.info("Article ID: {}", articleId);
        return webContentManagementService.deleteArticle(articleId);
    }

    @GetMapping("/public/getArticle/{articleId}")
    public ResponseEntity<APIResponse> getArticle(@PathVariable Integer articleId) {
        log.info("Article ID for getArticle By ID: {}", articleId);
        return webContentManagementService.getArticleById(articleId);
    }

    @PostMapping("/public/get-article-with-pagination")
    public ResponseEntity<APIResponse> getArticleWithPagination(@RequestParam Integer page, @RequestParam Integer size) {
        log.info("Page: {}, Size: {}", page, size);
        return webContentManagementService.getArticles(page, size);
    }

    @PutMapping("/updateArticle")
    public ResponseEntity<APIResponse> updateArticle(@RequestBody Article article) {
        log.info("Article update: {}", article.toString());
        return webContentManagementService.updateArticle(article);
    }
}
