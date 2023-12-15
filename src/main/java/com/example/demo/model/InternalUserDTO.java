package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class InternalUserDTO {
    private Long id;
    private String login;
    private String name;
    private String type;

    @JsonProperty("avatarUrl")
    private String avatar_url;

    @JsonProperty("createdAt")
    private LocalDateTime created_at;
    private Double calculations;
}
