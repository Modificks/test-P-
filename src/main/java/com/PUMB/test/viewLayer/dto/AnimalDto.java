package com.PUMB.test.viewLayer.dto;

import com.PUMB.test.domain.enums.Sex;
import com.PUMB.test.domain.enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalDto {

    @CsvBindByName(column = "Name")
    @XmlElement(name = "name")
    private String name;

    @CsvBindByName(column = "Weight")
    @XmlElement(name = "weight")
    private BigDecimal weight;

    @CsvBindByName(column = "Cost")
    @XmlElement(name = "cost")
    private BigDecimal cost;

    private Byte category;

    @CsvBindByName(column = "Type")
    @XmlElement(name = "type")
    @JsonIgnore
    private String typeTitle;

    @CsvBindByName(column = "Sex")
    @XmlElement(name = "sex")
    @JsonIgnore
    private String sexTitle;

    private Type type;

    private Sex sex;
}