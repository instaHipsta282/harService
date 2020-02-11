package com.instahipsta.harCRUD.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class Entry {
        private String pageref;
        private String startedDateTime;

        @JsonAlias("request")
        private RequestDto requestDto;

        private JsonNode response;
        private JsonNode cache;
        private JsonNode timings;
        private long time;
        private String _securityState;
        private String serverIPAddress;
        private int connection;
}

