package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.AuthenticationRequest;
import com.Jetcheck.Aplication.DTo.AuthenticationResponse;
import com.Jetcheck.Aplication.DTo.RecoverRequest;
import com.Jetcheck.Aplication.DTo.RegisterRequest;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Mapper.AuthenticationMapper;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RepositoryJDBC repositoryJDBC;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationMapper authenticationMapper;
    public AuthenticationResponse register(RegisterRequest request) {
        try{
            if (request.getUsername()==null){
                throw new PersonExceptions("Ingrese nombre de usario valido");
            }
            if (request.getPassword()==null){
                throw new PersonExceptions("Ingrese una contraseña valida");
            }
            if (userRepository.existsByUsername(request.getUsername())){
                throw new PersonExceptions("El nombre de usuario ya existe ingrese otro");
            } else if (userRepository.existsByCorreo(request.getEmail())) {
                throw new PersonExceptions("El correo ya existe ingrese otro");
            } else if (userRepository.existsById(request.getId())) {
                throw new PersonExceptions("Esta identificacion ya tiene un usuario asignado");
            }
            //Codificar Contraseña
            String passEncode = passwordEncoder.encode(request.getPassword());
            request.setPassword(passEncode);

            userRepository.save(authenticationMapper.mapperRegister(request));
            var jwtToken = jwtService.generateToken(authenticationMapper.mapperRegister(request));
            return AuthenticationResponse.builder()
                    .Token(jwtToken)
                    .Perfil(request.getProfile())
                    .build();
        }catch (PersonExceptions e){
            return AuthenticationResponse.builder()
                    .Token(e.getMessage())
                    .build();
        }

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (!userRepository.existsByUsername(request.getUsername()) || request.getUsername()==null){
            throw new PersonExceptions("Nombre de usuario no encontrado");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .Token(jwtToken)
                .Perfil(user.getPerfil())
                .build();
    }
    public ResponseEntity<String> recoverPassword(RecoverRequest request){
        if (userRepository.existsById(request.getId())){
            if (userRepository.existsByCorreo(request.getEmail())){
                if (request.getPassword().equals(request.getConfirmPassword())){
                    //Codificar Contraseña
                    String passEncode = passwordEncoder.encode(request.getPassword());
                    request.setPassword(passEncode);

                    repositoryJDBC.updatePassword(authenticationMapper.recoverMapper(request));
                    return ResponseEntity.ok("Contraseña Recuperada satifactoriamente");
                }else{
                    return ResponseEntity.badRequest().body("Las contraseñas no son iguales");
                }
            }else{
                return ResponseEntity.badRequest().body("Correo Inexistente");
            }
        }
        return ResponseEntity.badRequest().body("El usuario no existe, por favor Registrese en el aplicativo");
    }
    public ResponseEntity<String> existUser(AuthenticationRequest request){
        String nowPassword = request.getPassword();
        String password = repositoryJDBC.getPasswordByUsername(request.getUsername());
        if (userRepository.existsByUsername(request.getUsername()) && passwordEncoder.matches(nowPassword, password)){
            return ResponseEntity.badRequest().body("Ya hay una cuenta asociada a estas credenciales por favor inicie sesion");
        } else if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario se encuentra en uso, ingrese otro");
        }else{
            return ResponseEntity.ok("Correcto");
        }
    }

}
