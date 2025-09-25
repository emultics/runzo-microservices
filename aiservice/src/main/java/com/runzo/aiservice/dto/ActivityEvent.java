package com.runzo.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityEvent {
    private ActivityResponse activity;
    private Action action;
    private LocalDateTime timeStamp;

    private enum Action{
        CREATE,
        UPDATE
    }

    @Override
    public String toString() {
        return "ActivityEvent{" +
                "activity=" + activity +
                ", action=" + action +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
