package com.instahipsta.harCRUD.model.dto;

import com.instahipsta.harCRUD.model.entity.Request;

import java.util.List;

public class TestProfileDTO implements Transferable {

    private Long id;
    private List<Request> requests;
    private int requestsCount;

    public TestProfileDTO() {}

    public TestProfileDTO(Long id, List<Request> requests, int requestsCount) {
        this.id = id;
        this.requests = requests;
        this.requestsCount = requestsCount;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<Request> getRequests() { return requests; }

    public void setRequests(List<Request> requests) { this.requests = requests; }

    public int getRequestsCount() { return requestsCount; }

    public void setRequestsCount(int requestsCount) { this.requestsCount = requestsCount; }

}
