package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Entity.Entregas;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    protected List<Rutas> listRoutes(HttpServletRequest request){
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
    public int getNumberProjects(HttpServletRequest request){
        return uploadContentService.UploadProjects(request).size();
    }
    public int getNumberAdvances(HttpServletRequest request){
        return listAvances(request).size();
    }
    public int getNumberAssignment(HttpServletRequest request){
        int number=0;
        for (var item: listAvances(request)){
            List<Entregas> list= assignmentRepository.findAllByIdAvance(item.getId_avance());
            number+=list.size();
        }
        return number;
    }
    public List<Avances> orderDate(HttpServletRequest request) {
        List<Avances> avancesList = listAvances(request);
        avancesList.sort(Comparator.comparing(Avances::getFecha_creacion));
        return avancesList.stream().limit(3).collect(Collectors.toList());
    }
}
