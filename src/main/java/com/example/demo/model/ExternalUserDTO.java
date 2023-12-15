package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ExternalUserDTO {
    Long id;
    String login;
    String name;
    String type;
    String avatar_url;
    LocalDateTime created_at;
    Long followers;
    Long public_repos;
}
