package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.Request.AdvanceRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdvanceMapper {
    public Avances mapperAdvance(AdvanceRequest advanceRequest){
        return Avances.builder()
                .id_avance(advanceRequest.getAdvanceId())
                .idEstado(7)
                .fecha_creacion(new Date())
                .idRuta(advanceRequest.getRouteId())
                .descripcion(advanceRequest.getDescription())
                .titulo(advanceRequest.getTittle())
                .build();
    }
}
