package com.PUMB.test.service;

import java.util.List;

import com.PUMB.test.domain.entity.Animal;
import com.PUMB.test.viewLayer.dto.AnimalCriteria;
import com.PUMB.test.viewLayer.dto.AnimalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalService {

    Page<Animal> getAnimals(AnimalCriteria animalCriteria, Pageable pageable);

    List<AnimalDto> saveFromFile(MultipartFile file);
}
