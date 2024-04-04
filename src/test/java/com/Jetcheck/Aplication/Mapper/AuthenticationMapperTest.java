package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.RecoverRequest;
import com.Jetcheck.Aplication.DTo.RegisterRequest;
import com.Jetcheck.Aplication.DTo.UpdateUserRequest;
import com.Jetcheck.Aplication.Entity.Usuarios;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


class AuthenticationMapperTest {

    private AuthenticationMapper mapper;
    @BeforeEach
    void setUp() {
        mapper= new AuthenticationMapper();

    }

    @Test
    void mapperRegister() {
        RegisterRequest dto = new RegisterRequest(
                "123453",
                "jlarrada",
                "clave123",
                "Jose",
                "Larrada",
                "Maicao",
                "jose@mail.com",
                1
        );
        Usuarios usuarios =mapper.mapperRegister(dto);
        assertNotNull(usuarios.getId());
        assertEquals(dto.getId(),usuarios.getId());
        assertEquals(dto.getUsername(),usuarios.getUsername());
        assertEquals(dto.getPassword(),usuarios.getPassword());
        assertEquals(dto.getName(),usuarios.getNombres());
        assertEquals(dto.getLastname(),usuarios.getApellidos());
        assertEquals(dto.getCity(),usuarios.getCiudad());
        assertEquals(dto.getEmail(),usuarios.getCorreo());
        assertNotNull(usuarios.getPerfil());
    }

    @Test
    void recoverMapper() {
        RecoverRequest request = new RecoverRequest(
                "Jose@mail.com",
                "clave123",
                "clave123",
                "12342");
        Usuarios usuarios = mapper.recoverMapper(request);
        assertEquals(request.getId(),usuarios.getId());
        assertEquals(request.getPassword(),usuarios.getPassword());
    }

    @Test
    void mapperUpdate() {
        UpdateUserRequest request = new UpdateUserRequest(
                "Jose",
                "Larrada",
                "Valledupar",
                "vjose@mail.com");
        String username="Jlarrada";
        Usuarios usuarios = mapper.mapperUpdate(request,username);
        assertNotNull(username);
        assertEquals(request.getName(),usuarios.getNombres());
        assertEquals(request.getLastname(),usuarios.getApellidos());
        assertEquals(request.getCity(),usuarios.getCiudad());
        assertEquals(request.getEmail(),usuarios.getCorreo());
    }
}