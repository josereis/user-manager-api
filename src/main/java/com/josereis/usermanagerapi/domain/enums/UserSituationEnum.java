package com.josereis.usermanagerapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserSituationEnum {
    PENDING_ACTIVATION("1", "User waiting for activation"),
    ACTIVE("2", "Active user"),
    CANCELED("3", "Canceled user"),
    SUSPENDED("4", "Suspended user");

    private String code;
    private String description;
}
