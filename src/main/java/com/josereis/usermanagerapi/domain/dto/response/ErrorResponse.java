package com.josereis.usermanagerapi.domain.dto.response;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class    ErrorResponse {
    private String path;
    private Integer status;
    private String message;
    private Instant timestamp;
}
