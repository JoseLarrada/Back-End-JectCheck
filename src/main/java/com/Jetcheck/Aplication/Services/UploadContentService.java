package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadContentService {
    private final JwtServices jwtService;
    private final RepositoryJDBC repositoryJDBC;
    private final UserRepository userRepository;

    public String Validation(HttpServletRequest request){
        String Token=jwtService.tokenModify(request);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (jwtService.isTokenValid(Token, userDetails)) {
            String Username = jwtService.extractUsername(Token);
            return Username;
        }
        return null;
    }
    public List<Rutas> UploadProjects(HttpServletRequest request){
        String Username=Validation(request);
        if (Username!=null) {
            int perfil= repositoryJDBC.GetPerfilByUsername(Username);
            if (perfil==1){
                String id_student=repositoryJDBC.GetIdStudentByUsername(Username);
                List<Rutas> rutas = repositoryJDBC.GetRoutesById_Student(id_student);
                return rutas;
            }else if (perfil==2){
                List<Rutas> rutas = repositoryJDBC.GetRoutesByUsername(Username);
                return rutas;
            }
        }
        return null;
    }
    public ResponseEntity<Usuarios> UploadProfile(HttpServletRequest request){
       String Username=Validation(request);
       Optional<Usuarios> UsuarioOpt= userRepository.findByUsername(Username);
       return  UsuarioOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
