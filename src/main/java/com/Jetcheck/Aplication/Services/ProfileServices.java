package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.Request.ChangeRequest;
import com.Jetcheck.Aplication.DTo.Request.UpdateUserRequest;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Mapper.AuthenticationMapper;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServices {
    private final UserRepository userRepository;
    private final RepositoryJDBC repositoryJDBC;
    private final PasswordEncoder passwordEncoder;
    private final UploadContentService uploadContentService;
    private final AuthenticationMapper authenticationMapper;
    public ResponseEntity<String> changePassword(ChangeRequest changeRequest, HttpServletRequest request) {
        String username = uploadContentService.Validation(request);
        String nowPassword = changeRequest.getNowPassword();
        String password = repositoryJDBC.getPasswordByUsername(username);
        if (!passwordEncoder.matches(nowPassword, password)) {
            return ResponseEntity.badRequest().body("La contraseña antigua no es correcta");
        }
        if (!changeRequest.getNewPassword().equals(changeRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Las contraseñas no coinciden");
        }
        var user = Usuarios.builder()
                .password(passwordEncoder.encode(changeRequest.getConfirmPassword()))
                .username(username).build();
        repositoryJDBC.ChangePassword(user);
        return ResponseEntity.ok("Contraseña Cambiada Correctamente");
    }

    public ResponseEntity<String> updateUser(UpdateUserRequest updateRequest, HttpServletRequest request){
        String username = uploadContentService.Validation(request);
        if (username!=null){
            repositoryJDBC.updateUser(authenticationMapper.mapperUpdate(updateRequest,username));
            return ResponseEntity.ok("Datos actualizados correctamente");
        }
        return ResponseEntity.badRequest().body("Usuario no encontrado");
    }

    @Transactional
    public ResponseEntity<String> deleteUser(String password, HttpServletRequest request) {
        String username = uploadContentService.Validation(request);
        String storedPassword = repositoryJDBC.getPasswordByUsername(username);
        if (passwordEncoder.matches(password, storedPassword)) {
            userRepository.deleteByUsername(username);
            return ResponseEntity.ok("Cuenta eliminada correctamente");
        } else {
            return ResponseEntity.badRequest().body("Contraseña incorrecta");
        }
    }

}
