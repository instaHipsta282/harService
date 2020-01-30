package com.instahipsta.harCRUD.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TestProfile implements Entityable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_profile_id_seq")
    @SequenceGenerator(name = "test_profile_id_seq", sequenceName = "test_profile_id_seq", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests;
    private int requestsCount;

}
