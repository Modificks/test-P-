package com.PUMB.test.viewLayer.mapper;

import com.PUMB.test.domain.entity.Animal;
import com.PUMB.test.service.utils.AnimalUtils;
import com.PUMB.test.viewLayer.dto.AnimalDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnimalMapper extends BaseMapper<Animal, AnimalDto> {

    Animal toEntity(AnimalDto animalDto);

    AnimalDto toDto(Animal animal);

    @AfterMapping
    default void setCategory(AnimalDto animalDto, @MappingTarget Animal animal) {
        animal.setCategory(AnimalUtils.defineCategory(animalDto.getCost()));
    }
}