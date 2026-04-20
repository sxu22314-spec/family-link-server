package com.example.linkfamily.Model.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class PuzzleSyncEnvelope {
    @NotNull
    private PuzzleEventType eventType;

    @NotBlank
    private String roomId;

    @NotBlank
    private String puzzleId;

    @NotBlank
    private String actorRole; // grandson | grandparents

    @NotNull
    private Map<String, Object> payload;

    @NotBlank
    private String timestamp;
}
