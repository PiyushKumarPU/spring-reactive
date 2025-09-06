package com.learning.reactive.data.model;

import lombok.Getter;

@Getter
public enum AccountStatusEnum {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SUSPENDED("Suspended");

    private final String name;

    AccountStatusEnum(String name) {
        this.name = name;
    }
}
