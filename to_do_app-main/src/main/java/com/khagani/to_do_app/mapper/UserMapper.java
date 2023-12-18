package com.khagani.to_do_app.mapper;


import com.khagani.to_do_app.dto.RegistrationDTO;
import com.khagani.to_do_app.dto.ResetPasswordDto;
import com.khagani.to_do_app.dto.UserRequest;
import com.khagani.to_do_app.dto.UserResponse;
import com.khagani.to_do_app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@Mapper(imports = UUID.class)
public abstract class UserMapper {


    @Lazy
    @Autowired
    protected PasswordEncoder encoder;

    public abstract User mapToUserEntity(UserRequest userRequest);

    public abstract List<UserResponse> mapToUserResponseList(List<User> user);

    public abstract UserResponse mapToUserResponse(User user);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "false")
    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "password", expression = "java(encoder.encode(dto.getPassword()))")
    @Mapping(target = "uuid", expression = "java(UUID.randomUUID())")
    public abstract User toEntity(RegistrationDTO dto);


    @Mapping(target = "name", ignore = true)
    @Mapping(target = "otpCode", ignore = true)
    @Mapping(target = "tokenCreationDate", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "false")
    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "password", expression = "java(encoder.encode(resetPasswordDto.getPassword()))")
    public abstract User mapToEntity(ResetPasswordDto resetPasswordDto);

}
