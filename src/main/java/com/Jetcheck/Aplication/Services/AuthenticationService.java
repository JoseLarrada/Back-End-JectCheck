package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.AuthenticationRequest;
import com.Jetcheck.Aplication.DTo.AuthenticationResponse;
import com.Jetcheck.Aplication.DTo.RecoverRequest;
import com.Jetcheck.Aplication.DTo.RegisterRequest;
import com.Jetcheck.Aplication.Entity.Role;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final RepositoryJDBC repositoryJDBC;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.existsByUsername(request.getUsername())){
            throw new PersonExceptions("El nombre de usuario ya existe ingrese otro");
        } else if (repository.existsByCorreo(request.getEmail())) {
            throw new PersonExceptions("El correo ya existe ingrese otro");
        } else if (repository.existsById(request.getId())) {
            throw new PersonExceptions("Esta identificacion ya tiene un usuario asignado");
        }

        var user = Usuarios.builder()
                .id(request.getId())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombres(request.getName())
                .apellidos(request.getLastname())
                .ciudad(request.getCity())
                .correo(request.getEmail())
                .perfil(request.getProfile())
                .sexo(request.getSexuality())
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .Token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (!repository.existsByUsername(request.getUsername())){
            throw new PersonExceptions("Nombre de usuario no encontrado");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .Token(jwtToken)
                .build();
    }
    public String recoverpassword(RecoverRequest request){
        if (repository.existsById(request.getId())){
            if (repository.existsByCorreo(request.getEmail())){
                if (request.getPassword().equals(request.getConfirmPassword())){
                    Usuarios usuarios= Usuarios.builder()
                            .id(request.getId())
                            .password(passwordEncoder.encode(request.getPassword())).build();
                    repositoryJDBC.updatePassword(usuarios);
                    return "Contraseña Recuperada satifactoriamente";
                }else{
                    throw new PersonExceptions("Las contraseñas no son iguales");
                }
            }else{
                throw new PersonExceptions("Correo Inexistente");
            }
        }
        throw new PersonExceptions("El usuario no existe, por favor Registrese en el aplicativo");
    }
}
