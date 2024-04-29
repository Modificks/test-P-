package com.PUMB.test.domain.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Sex {

    MALE("male"),
    FEMALE("female");

    private final String title;

    public static Sex fromTitle(String title) {
        for (Sex sex : Sex.values()) {
            if (sex.getTitle().equalsIgnoreCase(title)) {
                return sex;
            }
        }
        return null;
    }
}