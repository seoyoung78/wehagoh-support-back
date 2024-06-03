package com.duzon.lulu.service.MSC.ExternalInterface.Infinitt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfo {
    @JsonProperty("USER_ID")
    String USER_ID;

    @JsonProperty("USER_NAME")
    String USER_NAME;

    @JsonProperty("PASSWORD")
    String PASSWORD;

    @JsonProperty("USER_LEVEL")
    String USER_LEVEL;

    @JsonProperty("USER_STATUS")
    String USER_STATUS;

    @JsonProperty("COMMENTS")
    String COMMENTS;
}
