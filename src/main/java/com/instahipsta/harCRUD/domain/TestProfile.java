package com.instahipsta.harCRUD.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.util.List;

@Entity
public class TestProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_profile_id_seq")
    @SequenceGenerator(name = "test_profile_id_seq", sequenceName = "test_profile_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Request> requests;
    private int requestsCount;

    public TestProfile(List<Request> requests) {
        this.requests = requests;
        this.requestsCount = requests.size();
    }

    public TestProfile() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Request> getRequests() { return requests; }

    public void setRequests(List<Request> requests) { this.requests = requests; }

    public int getRequestsCount() { return requestsCount; }

    public void setRequestsCount(int requestsCount) { this.requestsCount = requestsCount; }
}
