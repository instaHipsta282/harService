package com.instahipsta.harCRUD.model.dto.HAR;

import lombok.Data;

@Data
public class Timings {
    private int blocked;
    private int dns;
    private int connect;
    private int ssl;
    private int send;
    private int wait;
    private int receive;
}
