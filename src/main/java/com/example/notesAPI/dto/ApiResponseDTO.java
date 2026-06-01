package com.example.notesAPI.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"success", "message", "warning", "data"})
//T = type, a generic placeholder
public class ApiResponseDTO<T> {
    @Schema(name = "success", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean success;

    @Schema(name = "message", example = "resource found", requiredMode = Schema.RequiredMode.REQUIRED)
    private String message;

    @Schema(name = "warning", example = "label in request not found, note will not update its associated label", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String warning = null;

    @Schema(name = "data", example = "<String version of an object>", requiredMode = Schema.RequiredMode.REQUIRED)
    private T data;

    public ApiResponseDTO(boolean success, String message, T data) {
        this.success= success;
        this.message = message;
        this.data=data;
    }
}
