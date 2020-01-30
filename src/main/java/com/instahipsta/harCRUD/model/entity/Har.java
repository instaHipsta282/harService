package com.instahipsta.harCRUD.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Har implements Entityable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "har_id_seq")
    @SequenceGenerator(name = "har_id_seq", sequenceName = "har_id_seq", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(nullable = false)
    private String version;
    private String browser;
    private String browserVersion;
    private String fileName;

}
