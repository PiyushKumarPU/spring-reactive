package com.learning.reactive.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {

    ROLE_USER("ROLE_USER", "Role_User", "Represents a learner or participant in the system"),
    ROLE_ADMIN("ROLE_ADMIN", "Role_Admin", "Represents a super administrator with unrestricted access to all system features and settings."),
    ROLE_SALES("ROLE_SALES", "Role_Sales", "Represents an administrator with access to sales-related features, such as managing leads, tracking sales, and generating reports."),
    ROLE_OPERATIONS("ROLE_OPERATIONS", "ROLE_OPERATIONS", "Represents an administrator with access to operational features, such as managing workflows, overseeing processes, and handling day-to-day operations.");

    private final String name;
    private final String displayName;
    private final String description;
}
