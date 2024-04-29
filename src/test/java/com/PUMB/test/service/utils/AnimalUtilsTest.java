package com.PUMB.test.service.utils;

import com.PUMB.test.domain.enums.Sex;
import com.PUMB.test.domain.enums.Type;
import com.PUMB.test.viewLayer.dto.AnimalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AnimalUtilsTest {

    private AnimalDto animalDto;

    @BeforeEach
    void setUp() {
        animalDto = new AnimalDto();
    }

    @Test
    void defineCategory() {
        byte resultForFirstCategory1 = AnimalUtils.defineCategory(BigDecimal.ZERO);
        byte resultForFirstCategory2 = AnimalUtils.defineCategory(BigDecimal.ONE);

        assertEquals(1, resultForFirstCategory1);
        assertEquals(1, resultForFirstCategory2);

        byte resultForSecondCategory1 = AnimalUtils.defineCategory(BigDecimal.valueOf(21));
        byte resultForSecondCategory2 = AnimalUtils.defineCategory(BigDecimal.valueOf(30));

        assertEquals(2, resultForSecondCategory1);
        assertEquals(2, resultForSecondCategory2);

        byte resultForThirdCategory1 = AnimalUtils.defineCategory(BigDecimal.valueOf(41));
        byte resultForThirdCategory2 = AnimalUtils.defineCategory(BigDecimal.valueOf(56));

        assertEquals(3, resultForThirdCategory1);
        assertEquals(3, resultForThirdCategory2);

        byte resultForFourthCategory1 = AnimalUtils.defineCategory(BigDecimal.valueOf(62));
        byte resultForFourthCategory2 = AnimalUtils.defineCategory(BigDecimal.valueOf(78));

        assertEquals(4, resultForFourthCategory1);
        assertEquals(4, resultForFourthCategory2);
    }

    @Test
    void testIsObjectValidWithValidPetDto() {
        animalDto.setName("Fluffy");
        animalDto.setWeight(BigDecimal.valueOf(5.2));
        animalDto.setType(Type.CAT);
        animalDto.setSex(Sex.MALE);
        animalDto.setCost(BigDecimal.valueOf(100));

        assertTrue(AnimalUtils.isObjectValid(animalDto), "PetDto is valid");
    }

    @Test
    void testIsObjectValidWithNullName() {
        animalDto.setName(null);
        animalDto.setWeight(BigDecimal.valueOf(5.2));
        animalDto.setType(Type.DOG);
        animalDto.setSex(Sex.MALE);
        animalDto.setCost(BigDecimal.valueOf(100));

        assertFalse(AnimalUtils.isObjectValid(animalDto), "PetDto should not be considered valid with null name");
    }

    @Test
    void testIsObjectValidWithEmptyName() {
        animalDto.setName("");
        animalDto.setWeight(BigDecimal.valueOf(5.2));
        animalDto.setType(Type.CAT);
        animalDto.setSex(Sex.FEMALE);
        animalDto.setCost(BigDecimal.valueOf(100));

        assertFalse(AnimalUtils.isObjectValid(animalDto), "PetDto should not be considered valid with empty name");
    }
}