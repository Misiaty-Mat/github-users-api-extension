package com.example.demo.mappers;

import com.example.demo.model.ExternalUserDTO;
import com.example.demo.model.InternalUserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    InternalUserDTO toExternalUserDto(ExternalUserDTO userDTO);
}
