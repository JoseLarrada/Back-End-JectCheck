package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.ChangeRequest;
import com.Jetcheck.Aplication.DTo.RegisterRequest;
import com.Jetcheck.Aplication.DTo.UpdateUserRequest;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServices {
    private final UserRepository userRepository;
    private final RepositoryJDBC repositoryJDBC;
    private final PasswordEncoder passwordEncoder;
    private final UploadContentService uploadContentService;
    public ResponseEntity<String> ChangePassword(ChangeRequest changeRequest, HttpServletRequest request) {
        String Username = uploadContentService.Validation(request);
        String nowPassword = changeRequest.getNowPassword();
        String password = repositoryJDBC.getPasswordByUsername(Username);
        if (!passwordEncoder.matches(nowPassword, password)) {
            throw new PersonExceptions("La contraseña antigua no es correcta");
        }
        if (!changeRequest.getNewPassword().equals(changeRequest.getConfirmPassword())) {
            throw new PersonExceptions("Las contraseñas no coinciden");
        }
        var user = Usuarios.builder()
                .password(passwordEncoder.encode(changeRequest.getConfirmPassword()))
                .username(Username).build();
        repositoryJDBC.ChangePassword(user);
        return ResponseEntity.ok("Contraseña Cambiada Correctamente");
    }

    public ResponseEntity<String> UpdateUser(UpdateUserRequest UpdateRequest, HttpServletRequest request){
        String Username = uploadContentService.Validation(request);
        if (Username!=null){
            var user=Usuarios.builder()
                    .username(Username)
                    .nombres(UpdateRequest.getName())
                    .apellidos(UpdateRequest.getLastname())
                    .ciudad(UpdateRequest.getCity())
                    .correo(UpdateRequest.getEmail())
                    .build();
        repositoryJDBC.updateUser(user);
        return ResponseEntity.ok("Datos actualizados correctamente");
        }
        throw new  PersonExceptions("Usuario no encontrado");
    }

    @Transactional
    public ResponseEntity<String> DeleteUser(String password, HttpServletRequest request) {
        String username = uploadContentService.Validation(request);
        String storedPassword = repositoryJDBC.getPasswordByUsername(username);
        if (passwordEncoder.matches(password, storedPassword)) {
            userRepository.deleteByUsername(username);
            return ResponseEntity.ok("Cuenta eliminada correctamente");
        } else {
            throw new BadCredentialsException("Contraseña incorrecta");
        }
    }

}
