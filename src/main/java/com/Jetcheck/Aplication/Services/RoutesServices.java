package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.Request.RoutesRequest;
import com.Jetcheck.Aplication.DTo.Response.RoutesResponse;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Mapper.RoutesMapper;
import com.Jetcheck.Aplication.Repository.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutesServices {
    private final RoutesRepository routesRepository;
    private final IdGeneratorConfig generate;
    private final UploadContentService uploadContentService;
    private final RepositoryJDBC repositoryJDBC;
    private final RoutesMapper routesMapper;
    private final AreasService areasService;
    public ResponseEntity<String> addRoute(RoutesRequest request, HttpServletRequest http){
        try {
            if(request.getId()==null){
                request.setId(generate.IdGenerator());
            }
            if (request.getNameRoute()==null){
                return ResponseEntity.badRequest().body("Ingrese un nombre al proyecto");
            }
            if (routesRepository.existsById(request.getId())){
                return ResponseEntity.badRequest().body("Ya existe un proyecto asociado a este Id");
            }
            String username=uploadContentService.Validation(http);
            String id_Estudiante=repositoryJDBC.GetIdStudentByUsername(username);
            String idDocente = repositoryJDBC.getIdUserByFullName(request.getTeacher(),"profesores","id_docente");
            //Verificacion de autollamado de metodos
            if (request.getId_Member()!=null){
                request.setId_Member(nullMember(request.getId_Member()));
            }
            if (request.getId_Member2()!=null){
                request.setId_Member2(nullMember(request.getId_Member2()));
            }
            int idArea=areasService.getIdAreaByName(request.getIdArea());
            int idFacultly= areasService.getIdFacultlyByName(request.getIdFacultly());
            routesRepository.save(routesMapper.mapperRoutes(request,idDocente,id_Estudiante,idArea,idFacultly));
            return ResponseEntity.ok("Proyecto Guardado Correctamente");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @Transactional
    public ResponseEntity<String> disableRoute(String nameRoute,String idRoute){
        if (nameRoute==null){
            return ResponseEntity.badRequest().body("Ingrese un nombre valido");
        }else {
            Rutas response=routesRepository.findById(idRoute).orElse(null);
            assert response != null;
            if(nameRoute.equals(response.getTitulo())){
                routesRepository.deleteById(idRoute);
                return ResponseEntity.ok("Eliminado Correctamente");
            }else{
                return ResponseEntity.badRequest().body("No coinciden los nombres de los proyectos");
            }
        }
    }

    public ResponseEntity<String> updateRoute(RoutesRequest request, HttpServletRequest http){
        try {
            if (!routesRepository.existsById(request.getId())){
                return ResponseEntity.badRequest().body("No existe un proyecto asociado a este Id");
            }
            String username=uploadContentService.Validation(http);
            String id_Estudiante=repositoryJDBC.GetIdStudentByUsername(username);
            String idDocente = repositoryJDBC.getIdUserByFullName(request.getTeacher(),"profesores","id_docente");
            //Verificacion de autollamado de metodos
            request.setId_Member(nullMember(request.getId_Member()));
            request.setId_Member2(nullMember(request.getId_Member2()));
            int idArea=areasService.getIdAreaByName(request.getIdArea());
            int idFacultly= areasService.getIdFacultlyByName(request.getIdFacultly());
            routesRepository.save(routesMapper.mapperRoutes(request,idDocente,id_Estudiante,idArea,idFacultly));
            return ResponseEntity.ok("Proyecto Modificado Correctamente");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String nullMember(String member1){
        if (member1!=null){
            return repositoryJDBC.getIdUserByFullName(member1,"estudiantes","id_estudiante");
        }else{
            return null;
        }
    }
    public ResponseEntity<String> finishProject(String Id) {
        if (Id==null){
            return ResponseEntity.badRequest().body("Ingrese un Valor valido");
        }
        if (routesRepository.existsById(Id)){
            if (repositoryJDBC.getstatebyId(Id)==1){
                repositoryJDBC.uptadestate(2,Id);
                return ResponseEntity.ok("Proyecto finalizado Correctamente");
            }else{
                return ResponseEntity.badRequest().body("El proyecto ya ha sido finalizado");
            }
        }
        return ResponseEntity.badRequest().body("El proyecto no existe");
    }

    public ResponseEntity<String> acceptProject(String Id){
        if (Id==null){
            return ResponseEntity.badRequest().body("Ingrese un Valor valido");
        }
        if (routesRepository.existsById(Id)){
            if (repositoryJDBC.getstatebyId(Id)==5){
                repositoryJDBC.uptadestate(1,Id);
                return ResponseEntity.ok("Proyecto aceptado");
            }else{
                return ResponseEntity.badRequest().body("El proyecto ya ha sido aceptado");
            }
        }
        return ResponseEntity.notFound().build();
    }
    public ResponseEntity<RoutesResponse> findRoutesById(String id){
        Rutas route=routesRepository.findById(id).orElse(null);
        if (route!=null){
            String member1=repositoryJDBC.getFullNameUserById(route.getId_estudiante(), "estudiantes","id_estudiante");
            String member2=repositoryJDBC.getFullNameUserById(route.getId_integrante(), "estudiantes","id_estudiante");
            String member3=repositoryJDBC.getFullNameUserById(route.getId_integrante2(), "estudiantes","id_estudiante");
           var response=RoutesResponse.builder()
                   .title(route.getTitulo())
                   .area(areasService.getNameAreaById(route.getIdArea()))
                   .state(route.getIdEstado())
                   .description(route.getDescripcion())
                   .firstsMember(generate.getInitialName(member1))
                   .secondMember(generate.getInitialName(member2))
                   .thirdMember(generate.getInitialName(member3))
                   .build();
           return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<String> rejectProject(String id){
        if (routesRepository.existsById(id)){
            if (repositoryJDBC.getstatebyId(id)==5){
                repositoryJDBC.uptadestate(6,id);
                return ResponseEntity.ok("Proyecto Rechazado");
            }else {
                return ResponseEntity.badRequest().body("El proyecto ya ha sido aceptado");
            }
        }
        return ResponseEntity.notFound().build();
    }
}
