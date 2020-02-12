package com.instahipsta.harCRUD.model.dto.HAR;

import com.instahipsta.harCRUD.model.dto.HAR.Request.RequestDto;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class Entry {
        private String pageref;
        private String startedDateTime;
        @NotNull
        @Valid
        private RequestDto request;
        private Response response;
        private Cache cache;
        private Timings timings;
        private long time;
        private String _securityState;
        private String serverIPAddress;
        private int connection;
}

