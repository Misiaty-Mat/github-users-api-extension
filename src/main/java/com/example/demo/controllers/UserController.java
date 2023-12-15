package com.example.demo.controllers;

import com.example.demo.model.InternalUserDTO;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URISyntaxException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    public ResponseEntity<InternalUserDTO> getUserDetails(@PathVariable String login) throws URISyntaxException {
        return ResponseEntity.ok(userService.getUserDetailsByLogin(login));
    }
}
