package com.megacity.backend.guideline_management.service;

import com.megacity.backend.domain.entity.Guideline;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface GuidelineService {

    /**
     * Adds a new guideline to the system.
     *
     * @param guideline the {@link Guideline} object containing the guideline details to be added
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the operation
     */
    ResponseEntity<APIResponse> addNewGuideline(Guideline guideline);

    /**
     * Updates an existing guideline in the system.
     *
     * @param guideline the {@link Guideline} object containing the updated guideline details
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the update operation
     */
    ResponseEntity<APIResponse> updateGuideline(Guideline guideline);

    /**
     * Deletes a guideline from the system based on its ID.
     *
     * @param guidelineId the unique identifier of the guideline to be deleted
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the result of the deletion operation
     */
    ResponseEntity<APIResponse> deleteGuideline(Long guidelineId);

    /**
     * Retrieves a paginated list of all guideline records.
     *
     * @param page the page number to retrieve (zero-based)
     * @param size the number of guideline records per page
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the list of guideline records
     */
    ResponseEntity<APIResponse> fetchAllGuidelineRecords(int page, int size);

    /**
     * Retrieves a specific guideline by its ID.
     *
     * @param guidelineId the unique identifier of the guideline to fetch
     * @return a {@link ResponseEntity} containing an {@link APIResponse} with the requested guideline details
     */
    ResponseEntity<APIResponse> fetchGuidelineById(Long guidelineId);
}
