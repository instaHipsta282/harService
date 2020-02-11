package com.instahipsta.harCRUD.model.dto.Har;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.instahipsta.harCRUD.model.dto.RequestDto;
import com.instahipsta.harCRUD.model.dto.Response;
import lombok.Data;

@Data
public class Entry {
        private String pageref;
        private String startedDateTime;

        @JsonAlias("request")
        private RequestDto requestDto;

        private Response response;
        private Cache cache;
        private Timings timings;
        private long time;
        private String _securityState;
        private String serverIPAddress;
        private int connection;
}

