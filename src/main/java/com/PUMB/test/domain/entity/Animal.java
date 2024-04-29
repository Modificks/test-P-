package com.PUMB.test.domain.entity;

import com.PUMB.test.domain.enums.Sex;
import com.PUMB.test.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "animal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Animal implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "category")
    private Byte category;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;
}