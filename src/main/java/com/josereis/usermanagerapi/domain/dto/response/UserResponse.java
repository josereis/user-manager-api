package com.josereis.usermanagerapi.domain.dto.response;

import com.josereis.usermanagerapi.domain.enums.UserSituationEnum;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class UserResponse {
    private UUID uuid;
    private String name;
    private String email;
    private String username;
    private UserSituationEnum situation;
}
