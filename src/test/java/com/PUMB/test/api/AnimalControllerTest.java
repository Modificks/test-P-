package com.PUMB.test.api;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

import com.PUMB.test.exceptionHandler.exception.MissingFileException;
import com.PUMB.test.service.AnimalService;
import com.PUMB.test.viewLayer.dto.AnimalDto;
import com.PUMB.test.viewLayer.mapper.AnimalMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AnimalControllerTest {

    @Mock
    private AnimalService animalService;

    @Mock
    private AnimalMapper animalMapper;

    @InjectMocks
    private PetController petController;

    private MultipartFile csvFile;
    private MultipartFile xmlFile;
    private MultipartFile emptyFile;

    @BeforeEach
    void setUp() {
        csvFile = new MockMultipartFile("pets.csv", "pets.csv", "text/csv", "name,sex,cost,type\nFluffy,Male,100,Dog".getBytes());
        xmlFile = new MockMultipartFile("pets.xml", "pets.xml", "application/xml", "<pets><pet><name>Fluffy</name><sex>Male</sex><cost>100</cost><type>Dog</type></pet></pets>".getBytes());
        emptyFile = new MockMultipartFile("empty.txt", "empty.txt", "text/plain", new byte[0]);
    }

    @Test
    void testUploadPetsCsvSuccess() throws Exception {
        when(animalService.saveFromFile(any(MultipartFile.class))).thenReturn(Arrays.asList(new AnimalDto()));

        List<AnimalDto> result = petController.uploadPets(csvFile);
        assertNotNull(result);
        verify(animalService, times(1)).saveFromFile(csvFile);
    }

    @Test
    void testUploadPetsXmlSuccess() throws Exception {
        when(animalService.saveFromFile(any(MultipartFile.class))).thenReturn(Arrays.asList(new AnimalDto()));

        List<AnimalDto> result = petController.uploadPets(xmlFile);
        assertNotNull(result);
        verify(animalService, times(1)).saveFromFile(xmlFile);
    }

    @Test
    void testUploadPetsMissingFileException() {
        Exception exception = assertThrows(MissingFileException.class, () -> {
            petController.uploadPets(emptyFile);
        });

        String expectedMessage = "At least one file (CSV or XML) must be provided";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}