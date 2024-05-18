package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.AdvanceRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdvanceMapper {
    public Avances mapperAdvance(AdvanceRequest advanceRequest){
        return Avances.builder()
                .id_avance(advanceRequest.getAdvanceId())
                .idEstado(5)
                .fecha_creacion(new Date())
                .id_ruta(advanceRequest.getRouteId())
                .descripcion(advanceRequest.getDescription())
                .rubrica(advanceRequest.getRubric())
                .titulo(advanceRequest.getTittle())
                .build();
    }
}
