package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.RecoverRequest;
import com.Jetcheck.Aplication.DTo.RegisterRequest;
import com.Jetcheck.Aplication.DTo.UpdateUserRequest;
import com.Jetcheck.Aplication.Entity.Role;
import com.Jetcheck.Aplication.Entity.Usuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationMapper {
    private final PasswordEncoder passwordEncoder;
    public Usuarios mapperRegister(RegisterRequest registerRequest){
        return Usuarios.builder()
                .id(registerRequest.getId())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .nombres(registerRequest.getName())
                .apellidos(registerRequest.getLastname())
                .ciudad(registerRequest.getCity())
                .correo(registerRequest.getEmail())
                .perfil(registerRequest.getProfile())
                .role(Role.USER)
                .build();
    }

    public Usuarios recoverMapper(RecoverRequest recoverRequest){
        return Usuarios.builder()
                .id(recoverRequest.getId())
                .password(passwordEncoder.encode(recoverRequest.getPassword())).build();
    }

    public Usuarios mapperUpdate(UpdateUserRequest updateUserRequest, String username){
        return Usuarios.builder()
                .username(username)
                .nombres(updateUserRequest.getName())
                .apellidos(updateUserRequest.getLastname())
                .ciudad(updateUserRequest.getCity())
                .correo(updateUserRequest.getEmail())
                .build();
    }
}
