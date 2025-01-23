package com.megacity.backend.guideline_management.service;

import com.megacity.backend.domain.entity.Guideline;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface GuidelineService {


    /**
     * Adds a new guideline.
     *
     * @param guideline the guideline to add
     * @return the response entity with API response
     */
    ResponseEntity<APIResponse> addNewGuideline(Guideline guideline);

    /**
     * Updates an existing guideline.
     *
     * @param guideline the guideline to update
     * @return the response entity with API response
     */
    ResponseEntity<APIResponse> updateGuideline(Guideline guideline);

    /**
     * Deletes a guideline by ID.
     *
     * @param guidelineId the ID of the guideline to delete
     * @return the response entity with API response
     */
    ResponseEntity<APIResponse> deleteGuideline(Long guidelineId);

    /**
     * Fetches all guideline records.
     *
     * @return the response entity with API response
     */
    ResponseEntity<APIResponse> fetchAllGuidelineRecords();

    /**
     * Fetches a guideline by ID.
     *
     * @param guidelineId the ID of the guideline to fetch
     * @return the response entity with API response
     */
    ResponseEntity<APIResponse> fetchGuidelineById(Long guidelineId);
}
