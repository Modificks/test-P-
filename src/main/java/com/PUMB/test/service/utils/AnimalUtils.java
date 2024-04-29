package com.PUMB.test.service.utils;

import com.PUMB.test.viewLayer.dto.AnimalDto;

import java.math.BigDecimal;

public class AnimalUtils {

    public static Byte defineCategory(BigDecimal price) {
        if (price == null) {
            return null;
        }

        if (price.compareTo(BigDecimal.valueOf(62)) >= 0) {
            return 4;
        } else if (price.compareTo(BigDecimal.valueOf(41)) >= 0) {
            return 3;
        } else if (price.compareTo(BigDecimal.valueOf(21)) >= 0) {
            return 2;
        } else if (price.compareTo(BigDecimal.ZERO) >= 0) {
            return 1;
        } else {
            return null;
        }
    }

    public static boolean isObjectValid(AnimalDto animalDto) {
        return animalDto.getName() != null &&
                !animalDto.getName().isEmpty() &&
                animalDto.getWeight() != null &&
                animalDto.getType() != null &&
                animalDto.getSex() != null &&
                animalDto.getCost() != null &&
                animalDto.getCost().compareTo(BigDecimal.ZERO) > 0;
    }
}