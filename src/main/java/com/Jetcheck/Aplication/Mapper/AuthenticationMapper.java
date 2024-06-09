package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.Request.RecoverRequest;
import com.Jetcheck.Aplication.DTo.Request.RegisterRequest;
import com.Jetcheck.Aplication.DTo.Request.UpdateUserRequest;
import com.Jetcheck.Aplication.Entity.Role;
import com.Jetcheck.Aplication.Entity.Usuarios;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AuthenticationMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Usuarios mapperRegister(RegisterRequest registerRequest){
        return Usuarios.builder()
                .id(registerRequest.getId())
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
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
                .password(recoverRequest.getPassword()).build();
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
