package com.PUMB.test.domain.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Type {

    DOG("dog"),
    CAT("cat"),
    FISH("fish"),
    HAMSTER("hamster"),
    TURTLE("turtle"),
    SNAKE("snake"),
    LIZARD("lizard"),
    HORSE("horse"),
    SPIDER("spider"),
    MOUSE("mouse"),
    RAT("rat"),
    PARROT("parrot");

    private final String title;

    public static Type fromTitle(String title) {
        for (Type type : Type.values()) {
            if (type.getTitle().equalsIgnoreCase(title)) {
                return type;
            }
        }
        return null;
    }
}