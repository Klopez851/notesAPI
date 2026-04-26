package com.example.notesAPI.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
//T = type, a generic placeholder
public class ApiResponseDTO<T> {
    @Schema(name = "response",example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean response;

    @Schema(name = "message",example = "resource found", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(name = "data",example = "<String version of an object>", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data;

    public ApiResponseDTO(){}
}
