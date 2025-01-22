package com.megacity.backend.system_guidence.controller;

import com.megacity.backend.domain.entity.Guideline;
import com.megacity.backend.domain.response.APIResponse;
import com.megacity.backend.system_guidence.service.GuidelineService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/guideline")
@RequiredArgsConstructor
public class GuidelineController {

    @NonNull
    private final GuidelineService guidelineService;

    @GetMapping("/fetch-all")
    public ResponseEntity<APIResponse> fetchAllGuidelineRecords() {
        log.info("fetchAllGuidelineRecords start");
        var response = guidelineService.fetchAllGuidelineRecords();
        log.info("/fetch-all {}", response);
        return response;
    }

    @GetMapping("/{guidelineId}")
    public ResponseEntity<APIResponse> fetchGuidelineById(@PathVariable String guidelineId) {
        log.info("fetchGuidelineById {}", guidelineId);
        var response = guidelineService.fetchGuidelineById(Long.valueOf(guidelineId));
        log.info("fetchGuidelineById {}", response);
        return response;
    }

    @DeleteMapping
    public ResponseEntity<APIResponse> deleteGuideline(@RequestParam Long guidelineId) {
        log.info("deleteGuideline {}", guidelineId);
        var response = guidelineService.deleteGuideline(guidelineId);
        log.info("deleteGuideline {}", response);
        return response;
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateGuideline(@RequestBody Guideline guideline) {
        log.info("updateGuideline {}", guideline);
        var response = guidelineService.updateGuideline(guideline);
        log.info("updateGuideline {}", response);
        return response;
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addNewGuideline(@RequestBody Guideline guideline) {
        log.info("addNewGuideline {}", guideline);
        var response = guidelineService.addNewGuideline(guideline);
        log.info("addNewGuideline {}", response);
        return response;
    }
}
