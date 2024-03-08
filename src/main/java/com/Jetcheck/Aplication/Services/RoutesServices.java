package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.RoutesRequest;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
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
    public ResponseEntity<String> UpdateAddRuta(RoutesRequest request, HttpServletRequest http) {
        try{
            String Username=uploadContentService.Validation(http);
            String id_Estudiante=repositoryJDBC.GetIdStudentByUsername(Username);
            String idDocente = repositoryJDBC.GetIdTeacherByName(request.getTeacher());
            existValidation(nullmember1(request.getId_Member()),nullmember2(request.getId_Member2()),Username,idDocente);
            //Quiero Guardar
            if (request.getId()==null){
                UpdateOrSaveRoute(request,idDocente,id_Estudiante, Generate.IdGenerator(),5);
                return ResponseEntity.ok("Proyecto Guardado Correctamente");
            }else { //Quiero Actualizar
                UpdateOrSaveRoute(request,idDocente,id_Estudiante, request.getId(),5);
                return ResponseEntity.ok("Proyecto Modificado Correctamente");
            }

        }catch(PersonExceptions e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private void existValidation(String member1, String member2,String Username,String teacher){
        if (Username==null){
            throw new  PersonExceptions("Usuario no encontrado o no disponible");
        }
        if (member1 != null && !repositoryJDBC.ExistMember(member1)) {
            throw new PersonExceptions("Integrante 1 no encontrado");
        }
        if (member2 != null && !repositoryJDBC.ExistMember(member2)) {
            throw new PersonExceptions("Integrante 2 no encontrado");
        }
        if (teacher == null || !repositoryJDBC.ExistTeacher(teacher)) {
            throw new PersonExceptions("Docente no encontrado");
        }
    }
    private void UpdateOrSaveRoute(RoutesRequest request, String idDocente, String id_Estudiante,String id_ruta,int estado){
        var routes= Rutas.builder()
                .id_ruta(id_ruta)
                .id_estado(estado)
                .descripcion(request.getDescription())
                .id_docente(idDocente)
                .id_estudiante(id_Estudiante)
                .nombre(request.getNameRoute())
                .id_integrante(nullmember1(request.getId_Member()))
                .id_integrante2(nullmember2(request.getId_Member2()))
                .build();
        routesRepository.save(routes);
    }
    private String nullmember1(String member1){
        if (member1!=null){
            return repositoryJDBC.GetIdStudentByName(member1);
        }else{
            return null;
        }
    }
    private String nullmember2(String member2){
        if (member2!=null){
            return repositoryJDBC.GetIdStudentByName(member2);
        }else{
            return null;
        }
    }
    public ResponseEntity<String> DeleteRuta(String id){
        if (routesRepository.existsById(id)){
            routesRepository.deleteById(id);
            return ResponseEntity.ok("Eliminado Correctamente");
        }
        else{
            return ResponseEntity.badRequest().body("No se pudo eliminar");
        }
    }
    public ResponseEntity<String> FinishProject(String Id) {
        if (routesRepository.existsById(Id)){
            repositoryJDBC.uptadestate(2,Id);
            return ResponseEntity.ok("Proyecto finalizado Correctamente");
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<String> AcceptProject(String Id){
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
