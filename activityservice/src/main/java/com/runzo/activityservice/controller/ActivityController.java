package com.runzo.activityservice.controller;

import com.runzo.activityservice.dto.ActivityRequest;
import com.runzo.activityservice.service.ActivityHandlerService;
import com.runzo.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    @Autowired
    ActivityHandlerService activityHandlerService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> trackActivity(@Valid @RequestBody ActivityRequest activityRequest){
        ApiResponse<?> response = activityHandlerService.trackActivity(activityRequest);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.getErrorCode()).body(response);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ApiResponse<?>> getTrackById(@PathVariable("id") String activityId){
        ApiResponse<?> response = activityHandlerService.getActivityById(activityId);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.getErrorCode()).body(response);
    }

}
