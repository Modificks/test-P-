package com.PUMB.test.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.PUMB.test.domain.entity.Animal;
import com.PUMB.test.repository.AnimalRepository;
import com.PUMB.test.repository.AnimalCriteriaRepository;
import com.PUMB.test.viewLayer.dto.AnimalCriteria;
import com.PUMB.test.viewLayer.dto.AnimalDto;
import com.PUMB.test.viewLayer.mapper.AnimalMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;
    @Mock
    private AnimalMapper animalMapper;
    @Mock
    private AnimalCriteriaRepository animalCriteriaRepository;

    @InjectMocks
    private AnimalServiceImpl animalService;

    private MultipartFile csvFile;
    private MultipartFile xmlFile;
    private MultipartFile invalidFile;

    @BeforeEach
    void setUp() {
        csvFile = new MockMultipartFile("pets.csv", "pets.csv", "text/csv", "name,sex,cost,type\nFluffy,Male,100,Dog".getBytes());
        xmlFile = new MockMultipartFile("pets.xml", "pets.xml", "application/xml", "<animals><animal><name>Fluffy</name><sex>Male</sex><cost>100</cost><type>Dog</type></animal></animals>".getBytes());
        invalidFile = new MockMultipartFile("pets.xyz", "pets.xyz", "application/xyz", "".getBytes());
    }

    @Test
    void testSaveFromFileCSV() throws Exception {
        when(animalMapper.toEntity(any(AnimalDto.class))).thenAnswer(invocation -> new Animal());
        when(animalMapper.toDto(any(Animal.class))).thenAnswer(invocation -> new AnimalDto());

        List<AnimalDto> result = animalService.saveFromFile(csvFile);

        assertNotNull(
        verify(animalRepository, times(1)).saveAll(anyList()));
    }

    @Test
    void testSaveFromFileXML() throws Exception {
        when(animalMapper.toEntity(any(AnimalDto.class))).thenAnswer(invocation -> new Animal());
        when(animalMapper.toDto(any(Animal.class))).thenAnswer(invocation -> new AnimalDto());

        List<AnimalDto> result = animalService.saveFromFile(xmlFile);

        assertNotNull(result);
        verify(animalRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testSaveFromFileUnsupportedType() {
        assertThrows(UnsupportedMediaTypeStatusException.class, () -> animalService.saveFromFile(invalidFile));
    }

    @Test
    void testGetPets() {
        AnimalCriteria criteria = new AnimalCriteria();
        Pageable pageable = mock(Pageable.class);
        Page<Animal> expectedPage = mock(Page.class);

        when(animalCriteriaRepository.findWithFilters(criteria, pageable)).thenReturn(expectedPage);

        Page<Animal> result = animalService.getAnimals(criteria, pageable);

        assertNotNull(result);
        assertEquals(expectedPage, result);
        verify(animalCriteriaRepository, times(1)).findWithFilters(criteria, pageable);
    }
}