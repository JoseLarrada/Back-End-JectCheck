package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.RoutesRequest;
import com.Jetcheck.Aplication.Entity.Rutas;
import org.springframework.stereotype.Service;

@Service
public class RoutesMapper {
    public Rutas mapperRoutes(RoutesRequest routesRequest,String idDocente, String id_Estudiante){
        return Rutas.builder()
                .id_ruta(routesRequest.getId())
                .id_estado(5)
                .descripcion(routesRequest.getDescription())
                .id_docente(idDocente)
                .id_estudiante(id_Estudiante)
                .nombre(routesRequest.getNameRoute())
                .id_integrante(routesRequest.getId_Member())
                .id_integrante2(routesRequest.getId_Member2())
                .build();
    }
}