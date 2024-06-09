package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.Request.RateRequest;
import com.Jetcheck.Aplication.Entity.Calificaciones;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateMapper {
    private final IdGeneratorConfig generatorConfig;
    public Calificaciones rateMapper(RateRequest rateRequest){
        return Calificaciones.builder()
                .idCalificacion(rateRequest.getIdRate()==null
                        ? generatorConfig.IdGenerator() : rateRequest.getIdRate())
                .valorCalificacion(rateRequest.getRateValue())
                .comentario(rateRequest.getComment())
                .idEntrega(rateRequest.getIdAssignment())
                .build();
    }
}
