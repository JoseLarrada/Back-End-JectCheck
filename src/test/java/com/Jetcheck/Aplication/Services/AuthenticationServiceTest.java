package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.AuthenticationRequest;
import com.Jetcheck.Aplication.DTo.AuthenticationResponse;
import com.Jetcheck.Aplication.DTo.RegisterRequest;
import com.Jetcheck.Aplication.Entity.Role;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Mapper.AuthenticationMapper;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;
    //Inyecciones de otras clases

    @Mock //Creamos un simulacro para esa variable
    private UserRepository userRepository;
    @Mock
    private RepositoryJDBC repositoryJDBC;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtServices jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthenticationMapper authenticationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //Abrir y buscar los mocks disponibles en esta clase
    }

    @Test
    void register() {
        //Inicializacion
        RegisterRequest request = new RegisterRequest("12344","skay0340","hola","Jose","Larrada","Maicao","jose@mail.com",1);
        Usuarios user = new Usuarios("12344","skay0340","Larrada","Jose","Maicao","jose@mail.com",1,"Contrasena",Role.USER);
        Usuarios userCript = new Usuarios("12344","skay0340","Larrada","Jose","Maicao","jose@mail.com",1,"ContrasenaEncriptada",Role.USER);

        //LLamado de los mock Para el simulamiento de las dependencias
        when(passwordEncoder.encode(request.getId())).thenReturn(userCript.getPassword());
        when(authenticationMapper.mapperRegister(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(userCript);
        when(jwtService.generateToken(userCript)).thenReturn("Token");
        //Cuerpo
        AuthenticationResponse response = authenticationService.register(request);
        //Resultado
        assertNotEquals(request.getPassword(),userCript.getPassword());
        assertEquals(request.getProfile(),response.getPerfil());
    }

    @Test
    public void register_withNullUsername() {
        // Inicialización
        RegisterRequest request = new RegisterRequest("12344", null, "hola", "Jose", "Larrada", "Maicao", "jose@mail.com", 1);

        // Simulación de los métodos del repositorio
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByCorreo(request.getEmail())).thenReturn(false);
        when(userRepository.existsById(request.getId())).thenReturn(false);

        // Cuerpo
        Exception exception = assertThrows(PersonExceptions.class, () -> {
            authenticationService.register(request);
        });

        // Resultado
        String expectedMessage = "Ingrese nombre de usario valido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void register_withNullPassword() {
        // Inicialización
        RegisterRequest request = new RegisterRequest("12344", "skay0340", null, "Jose", "Larrada", "Maicao", "jose@mail.com", 1);

        // Simulación de los métodos del repositorio
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByCorreo(request.getEmail())).thenReturn(false);
        when(userRepository.existsById(request.getId())).thenReturn(false);

        // Cuerpo
        Exception exception = assertThrows(PersonExceptions.class, () -> {
            authenticationService.register(request);
        });

        // Resultado
        String expectedMessage = "Ingrese una contraseña valida";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void register_withUserExist(){
        RegisterRequest request = new RegisterRequest("12344", "skay0340", "hola", "Jose", "Larrada", "Maicao", "jose@mail.com", 1);
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        Exception exception = assertThrows(PersonExceptions.class, () -> {
            authenticationService.register(request);
        });

        String expectedMessage = "El nombre de usuario ya existe ingrese otro";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void register_withEmailExist(){
        RegisterRequest request = new RegisterRequest("12344", "skay0340", "hola",
                "Jose", "Larrada", "Maicao", "jose@mail.com", 1);

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByCorreo(request.getEmail())).thenReturn(true);


        Exception exception = assertThrows(PersonExceptions.class, () -> {
            authenticationService.register(request);
        });

        String expectedMessage = "El correo ya existe ingrese otro";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void register_withIdentificationExist(){
        RegisterRequest request = new RegisterRequest("12344", "skay0340", "hola", "Jose", "Larrada", "Maicao", "jose@mail.com", 1);

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByCorreo(request.getEmail())).thenReturn(false);
        when(userRepository.existsById(request.getId())).thenReturn(true);

        Exception exception = assertThrows(PersonExceptions.class, () -> {
            authenticationService.register(request);
        });

        String expectedMessage = "Esta identificacion ya tiene un usuario asignado";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }
}