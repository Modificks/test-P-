package com.PUMB.test.service.impl;

import com.PUMB.test.exceptionHandler.exception.FileParsingException;
import com.PUMB.test.exceptionHandler.exception.MissingFileException;
import com.PUMB.test.service.utils.AnimalUtils;
import com.PUMB.test.domain.entity.Animal;
import com.PUMB.test.domain.enums.Sex;
import com.PUMB.test.domain.enums.Type;
import com.PUMB.test.repository.AnimalCriteriaRepository;
import com.PUMB.test.repository.AnimalRepository;
import com.PUMB.test.service.AnimalService;
import com.PUMB.test.viewLayer.dto.AnimalCriteria;
import com.PUMB.test.viewLayer.dto.AnimalDto;
import com.PUMB.test.viewLayer.dto.AnimalWrapper;
import com.PUMB.test.viewLayer.mapper.AnimalMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AnimalCriteriaRepository animalCriteriaRepository;

    private final AnimalMapper animalMapper;

    @Override
    @Transactional
    public List<AnimalDto> saveFromFile(MultipartFile file) {
        if (file == null || file.getContentType() == null) {
            throw new MissingFileException("Missing file");
        }

        List<AnimalDto> dtos = switch (file.getContentType()) {
            case "application/xml", "text/xml" -> readXmlFile(file);
            case "text/csv" -> readCsvFile(file);
            default -> throw new UnsupportedMediaTypeStatusException("Unsupported type: " + file.getContentType());
        };

        List<Animal> animals = dtos.stream()
                .peek(o -> {
                    o.setSex(Sex.fromTitle(o.getSexTitle()));
                    o.setType(Type.fromTitle(o.getTypeTitle()));
                    o.setCategory(AnimalUtils.defineCategory(o.getCost()));
                })
                .filter(AnimalUtils::isObjectValid)
                .map(animalMapper::toEntity)
                .toList();

        animalRepository.saveAll(animals);

        return animals.stream()
                .map(animalMapper::toDto)
                .toList();
    }

    private List<AnimalDto> readCsvFile(MultipartFile csvFile) {
        try (Reader reader = new BufferedReader(new InputStreamReader(csvFile.getInputStream()))) {
            CsvToBean<AnimalDto> csvToBean = new CsvToBeanBuilder<AnimalDto>(reader)
                    .withType(AnimalDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            return csvToBean.stream()
                    .toList();

        } catch (Exception e) {
            throw new FileParsingException("Error during parsing file");
        }
    }

    private List<AnimalDto> readXmlFile(MultipartFile xmlFile) {
        try (Reader reader = new BufferedReader(new InputStreamReader(xmlFile.getInputStream()))) {
            JAXBContext jaxbContext = JAXBContext.newInstance(AnimalWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            AnimalWrapper wrapper = (AnimalWrapper) unmarshaller.unmarshal(reader);

            return wrapper.getPets();

        } catch (Exception ex) {
            throw new FileParsingException("Error during parsing file");
        }
    }

    @Override
    public Page<Animal> getAnimals(AnimalCriteria animalCriteria, Pageable pageable) {
        return animalCriteriaRepository.findWithFilters(animalCriteria, pageable);
    }
}