package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.mappers.UserMapper;
import com.example.demo.model.ExternalUserDTO;
import com.example.demo.model.InternalUserDTO;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RestTemplate restTemplate;

    @Value("${resource-server.url}")
    String resourceServerUrl;

    public InternalUserDTO getUserDetailsByLogin(String login) throws URISyntaxException {
        ExternalUserDTO userDTO = getUserDTO(login);
        incrementRequestCountForLogin(login);
        InternalUserDTO responseUserDTO = userMapper.toExternalUserDto(userDTO);
        responseUserDTO.setCalculations(getCalculations(userDTO));

        return responseUserDTO;
    }

    private ExternalUserDTO getUserDTO(String login) throws URISyntaxException {
        final URI uri = new URI(resourceServerUrl + "/" + login);

        return restTemplate.getForObject(uri, ExternalUserDTO.class);
    }

    private void incrementRequestCountForLogin(String login) {
        userRepository.findByLogin(login).ifPresentOrElse(
                user -> {
                    user.setRequest_count(user.getRequest_count() + 1);
                    userRepository.save(user);
                },
                () -> {
                    User user = User.builder()
                            .login(login)
                            .request_count(1L)
                            .build();
                    userRepository.save(user);
                }
        );
    }

    private Double getCalculations(ExternalUserDTO userDTO) {
        if (userDTO.getFollowers() == 0)
            return 0.0;

        return 6.0 / userDTO.getFollowers() * (2 + userDTO.getPublic_repos());
    }
}
