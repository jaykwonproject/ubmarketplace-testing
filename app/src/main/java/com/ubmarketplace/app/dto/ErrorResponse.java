package com.ubmarketplace.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String type;
    @Builder.Default
    private Long timestamp = Instant.now().toEpochMilli();
}
