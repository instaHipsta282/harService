package com.instahipsta.harCRUD.model.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_id_seq")
    @SequenceGenerator(name = "request_id_seq", sequenceName = "request_id_seq", allocationSize = 1)
    private long id;

    @Length(max = 65000)
    private String url;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode body;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode headers;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode params;

    private HttpMethod method;
    private Double perc = 0.0;

    @ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private TestProfile testProfile;

}
