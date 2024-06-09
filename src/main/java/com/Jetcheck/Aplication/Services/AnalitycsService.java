package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.Response.AnalitycsResponse;
import com.Jetcheck.Aplication.DTo.Response.InteractiveSearch;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Entity.Entregas;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalitycsService {
    private final UploadContentService uploadContentService;
    private final AdvanceRepository advanceRepository;
    private final AssignmentRepository assignmentRepository;
    private final RepositoryJDBC repositoryJDBC;
    private final UserRepository userRepository;
    private List<Rutas> listRoutes(HttpServletRequest request){
        return uploadContentService.UploadProjects(request);
    }
    private List<Avances> listAvances(HttpServletRequest request) {
        List<Avances> lista = new ArrayList<>();
        for (var item : listRoutes(request)) {
            List<Avances> lista2 = advanceRepository.findAllByIdRuta(item.getId_ruta());
            lista.addAll(lista2);
        }
        return lista;
    }
    private int getNumberAssignment(HttpServletRequest request){
        int number=0;
        for (var item: listAvances(request)){
            List<Entregas> list= assignmentRepository.findAllByIdAvance(item.getId_avance());
            number+=list.size();
        }
        return number;
    }
    private List<Avances> orderDate(HttpServletRequest request) {
        List<Avances> avancesList = listAvances(request);
        avancesList.sort(Comparator.comparing(Avances::getFecha_creacion));
        return avancesList.stream().limit(3).collect(Collectors.toList());
    }
    private List<InteractiveSearch> getAreas(HttpServletRequest request){
        String username=uploadContentService.Validation(request);
        Usuarios user=userRepository.findByUsername(username).orElse(null);
        if (repositoryJDBC.GetPerfilByUsername(username)==1){
            return repositoryJDBC.getMostAreas("estudiantes","id_estudiante",user.getId());
        }else {
            return repositoryJDBC.getMostAreas("profesores","id_docente",user.getId());
        }
    }
    public ResponseEntity<AnalitycsResponse> returnAnalitycs(HttpServletRequest request){
        return ResponseEntity.ok(
                AnalitycsResponse.builder()
                        .numberProjects(uploadContentService.UploadProjects(request).size())
                        .numberAdvances(listAvances(request).size())
                        .numberAssignments(getNumberAssignment(request))
                        .advances(orderDate(request))
                        .areas(getAreas(request))
                        .build()
        );
    }
}
