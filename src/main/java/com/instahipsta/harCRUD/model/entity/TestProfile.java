package com.instahipsta.harCRUD.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TestProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_profile_id_seq")
    @SequenceGenerator(name = "test_profile_id_seq", sequenceName = "test_profile_id_seq", allocationSize = 1)
    @Setter(AccessLevel.NONE)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests;
    private int requestsCount;

}
