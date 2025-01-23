package com.megacity.backend.manager_management.service;

import com.megacity.backend.domain.entity.Manager;
import com.megacity.backend.domain.response.APIResponse;
import org.springframework.http.ResponseEntity;

public interface ManagerService {


    /**
     * Updates an existing manager.
     *
     * @param manager the manager entity to be updated
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> UpdateManager(Manager manager);

    /**
     * Retrieves a manager by their ID.
     *
     * @param managerId the ID of the manager to be retrieved
     * @return a ResponseEntity containing the APIResponse with the manager details
     */
    ResponseEntity<APIResponse> getManagerById(Integer managerId);

    /**
     * Retrieves all managers.
     *
     * @return a ResponseEntity containing the APIResponse with a list of all managers
     */
    ResponseEntity<APIResponse> getAllManagers();

    /**
     * Creates a new manager.
     *
     * @param manager the manager entity to be created
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> createManager(Manager manager);

    /**
     * Deletes a manager by their ID.
     *
     * @param managerId the ID of the manager to be deleted
     * @return a ResponseEntity containing the APIResponse
     */
    ResponseEntity<APIResponse> deleteManager(Integer managerId);
}
