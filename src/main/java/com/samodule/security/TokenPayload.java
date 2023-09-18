package com.samodule.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenPayload {

    @JsonProperty
    public long userId;
    @JsonProperty
    public String username;
    @JsonProperty
    public String password;
    @JsonProperty
    public String status;
    @JsonProperty
    public long expirationTime;

    @Override
    public String toString() {
        return "TokenPayload{" +
                "userId=" + userId +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
