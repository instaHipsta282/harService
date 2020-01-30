package com.instahipsta.harCRUD.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Request implements Entityable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_id_seq")
    @SequenceGenerator(name = "request_id_seq", sequenceName = "request_id_seq", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private long id;
    @Length(max = 65000)
    private String url;
    @Lob
    private String body;
    @ElementCollection
    @MapKeyColumn(name = "headers_key", length = 25000)
    @Column(name = "headers_val", length = 25000)
    private Map<String, String> headers;
    @ElementCollection
    @MapKeyColumn(name = "params_key", length = 25000)
    @Column(name = "params_val", length = 25000)
    private Map<String, String> params;
    private HttpMethod method;
    private Double perc;
    @ManyToOne
    private TestProfile testProfile;

}
