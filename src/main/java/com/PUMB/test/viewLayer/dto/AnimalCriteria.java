package com.PUMB.test.viewLayer.dto;

import com.PUMB.test.domain.enums.Sex;
import com.PUMB.test.domain.enums.Type;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Set;


@Getter
@Setter
public class AnimalCriteria {

    private Byte category;
    private Set<Type> types;
    private Set<Sex> sexes;
    private Sort.Direction sortDirection;
    private String sortField;
}