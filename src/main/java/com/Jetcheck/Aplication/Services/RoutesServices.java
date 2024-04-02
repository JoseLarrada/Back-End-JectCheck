package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.RoutesRequest;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Mapper.RoutesMapper;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.RoutesRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutesServices {
    private final RoutesRepository routesRepository;
    private final IdGeneratorConfig Generate;
    private final UploadContentService uploadContentService;
    private final RepositoryJDBC repositoryJDBC;
    private final RoutesMapper routesMapper;
    public ResponseEntity<String> addRoute(RoutesRequest request, HttpServletRequest http){
        try {
            request.setId(Generate.IdGenerator());
            if (routesRepository.existsById(request.getId())){
                return ResponseEntity.badRequest().body("Ya existe un proyecto asociado a este Id");
            }
            String username=uploadContentService.Validation(http);
            String id_Estudiante=repositoryJDBC.GetIdStudentByUsername(username);
            String idDocente = repositoryJDBC.GetIdTeacherByName(request.getTeacher());
            //Verificacion de autollamado de metodos
            request.setId_Member(nullMember1(request.getId_Member()));
            request.setId_Member2(nullMember2(request.getId_Member2()));
            routesRepository.save(routesMapper.mapperRoutes(request,idDocente,id_Estudiante));
            return ResponseEntity.ok("Proyecto Guardado Correctamente");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public ResponseEntity<String> updateRoute(RoutesRequest request, HttpServletRequest http){
        try {
            if (!routesRepository.existsById(request.getId())){
                return ResponseEntity.badRequest().body("No existe un proyecto asociado a este Id");
            }
            String username=uploadContentService.Validation(http);
            String id_Estudiante=repositoryJDBC.GetIdStudentByUsername(username);
            String idDocente = repositoryJDBC.GetIdTeacherByName(request.getTeacher());
            //Verificacion de autollamado de metodos
            request.setId_Member(nullMember1(request.getId_Member()));
            request.setId_Member2(nullMember2(request.getId_Member2()));
            routesRepository.save(routesMapper.mapperRoutes(request,idDocente,id_Estudiante));
            return ResponseEntity.ok("Proyecto Modificado Correctamente");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String nullMember1(String member1){
        if (member1!=null){
            return repositoryJDBC.GetIdStudentByName(member1);
        }else{
            return null;
        }
    }
    private String nullMember2(String member2){
        if (member2!=null){
            return repositoryJDBC.GetIdStudentByName(member2);
        }else{
            return null;
        }
    }
    public ResponseEntity<String> finishProject(String Id) {
        if (routesRepository.existsById(Id)){
            repositoryJDBC.uptadestate(2,Id);
            return ResponseEntity.ok("Proyecto finalizado Correctamente");
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<String> acceptProject(String Id){
        if (routesRepository.existsById(Id)){
            if (repositoryJDBC.getstatebyId(Id)==5){
                repositoryJDBC.uptadestate(1,Id);
                return ResponseEntity.ok("Proyecto aceptado");
            }else{
                ResponseEntity.badRequest().body("El proyecto ya ha sido aceptado");
            }
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<List<Rutas>> filterRoutes(int state) {
        return switch (state) {
            case 1 -> ResponseEntity.ok(repositoryJDBC.findById_estado(1));
            case 2 -> ResponseEntity.ok(repositoryJDBC.findById_estado(2));
            case 3 -> ResponseEntity.ok(repositoryJDBC.findById_estado(3));
            case 4 -> ResponseEntity.ok(repositoryJDBC.findById_estado(4));
            case 5 -> ResponseEntity.ok(repositoryJDBC.findById_estado(5));
            default -> ResponseEntity.notFound().build();
        };
    }
}
