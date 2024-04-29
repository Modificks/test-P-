package com.PUMB.test.api;

import com.PUMB.test.domain.entity.Animal;
import com.PUMB.test.exceptionHandler.exception.MissingFileException;
import com.PUMB.test.service.AnimalService;
import com.PUMB.test.viewLayer.dto.AnimalCriteria;
import com.PUMB.test.viewLayer.dto.AnimalDto;
import com.PUMB.test.viewLayer.mapper.AnimalMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "Pet Controller")
public class PetController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @Operation(
            summary = "Method to upload files and read values from them",
            description = "In this method you can upload a CSV or XML file to store objects from it in DB, " +
                    "also every object will be validated to save only the objects that have all blanked fields",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unsupported Media Type",
                            responseCode = "415"
                    ),
                    @ApiResponse(
                            description = "Bad request",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Internal Server Error",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping(value = "/files/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<AnimalDto> uploadPets(@NotNull @RequestPart MultipartFile file) {
        if (file.isEmpty()) {
            throw new MissingFileException("At least one file (CSV or XML) must be provided");
        }

        if (file.getContentType() != null &&
                (!file.getContentType().equals("text/csv") &&
                        !file.getContentType().equals("application/xml") &&
                        !file.getContentType().equals("text/xml"))) {
            throw new UnsupportedMediaTypeStatusException("Provide file with extension CSV or XML");
        }

        return animalService.saveFromFile(file);
    }

    @Operation(
            summary = "Method to filter and sorting objects",
            description = "In this method, you can choose any sorting and a few filter parameters " +
                    "to get only the objects that you want to see by your criteria " +
                    "if you don`t have any criteria it will return all objects",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/filter")
    public Page<AnimalDto> filterPets(Pageable pageable, AnimalCriteria animalCriteria) {
        Page<Animal> pets = animalService.getAnimals(animalCriteria, pageable);

        return pets
                .map(animalMapper::toDto);
    }
}