package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.RateRequest;
import com.Jetcheck.Aplication.Entity.Calificaficiones;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateMapper {
    private final IdGeneratorConfig generatorConfig;
    public Calificaficiones rateMapper(RateRequest rateRequest){
        return Calificaficiones.builder()
                .idCalificacion(rateRequest.getIdRate()==null
                        ? generatorConfig.IdGenerator() : rateRequest.getIdRate())
                .valorCalificacion(rateRequest.getRateValue())
                .comentario(rateRequest.getComment())
                .idEntrega(rateRequest.getIdAssignment())
                .build();
    }
}
