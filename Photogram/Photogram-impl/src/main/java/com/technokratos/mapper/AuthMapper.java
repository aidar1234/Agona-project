package com.technokratos.mapper;

import com.technokratos.dto.response.AuthResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthResponse getAuthResponse(String accessToken, String refreshToken);
}
