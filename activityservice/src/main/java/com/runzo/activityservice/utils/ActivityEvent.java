package com.runzo.activityservice.utils;

import com.runzo.activityservice.dto.ActivityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ActivityEvent {
    private ActivityResponse activity;
    private Action action;
    private LocalDateTime timeStamp;
}
