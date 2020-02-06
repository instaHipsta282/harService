package com.instahipsta.harCRUD.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_profile_id_seq")
    @SequenceGenerator(name = "test_profile_id_seq", sequenceName = "test_profile_id_seq", allocationSize = 1)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests;

    private int requestsCount;

}
