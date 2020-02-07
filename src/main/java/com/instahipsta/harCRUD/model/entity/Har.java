package com.instahipsta.harCRUD.model.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Har {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "har_id_seq")
    @SequenceGenerator(name = "har_id_seq", sequenceName = "har_id_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String version;

    @NonNull
    private String browser;

    @NonNull
    private String browserVersion;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    @NonNull
    private JsonNode content;

}
