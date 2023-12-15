package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ExternalUserDTO {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatar_url;
    private LocalDateTime created_at;
    private Long followers;
    private Long public_repos;
}
