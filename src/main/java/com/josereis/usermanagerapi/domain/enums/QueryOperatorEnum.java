package com.josereis.usermanagerapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueryOperatorEnum {
    IN("IN"),
    LIKE("LIKE"),
    EQUAL("EQUAL"),
    IS_NULL("IS NULL"),
    NOT_EQUAL("NOT EQUAL"),
    IS_NOT_NULL("IS NOT NULL"),
    LESS_THAN("LESS THAN"),
    GREATER_THAN("GREATER THAN"),
    LESS_THAN_OR_EQUAL("LESS THAN OR EQUAL"),
    GREATER_THAN_OR_EQUAL("GREATER THAN OR EQUAL")
    ;

    private String description;
}