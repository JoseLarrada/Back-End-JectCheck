package com.Jetcheck.Aplication.Mapper;

import com.Jetcheck.Aplication.DTo.Request.AssignmentRequest;
import com.Jetcheck.Aplication.Entity.Entregas;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class AssignmentMapper {
    public Entregas mapperAssignment(AssignmentRequest assignmentRequest){
        LocalDateTime now = LocalDateTime.now();
        Date fechaCreacion = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        return Entregas.builder()
                .idAvance(assignmentRequest.getIdAdvance())
                .comentario(assignmentRequest.getComment())
                .id_entrega(assignmentRequest.getIdAssignment())
                .fechaEntrega(fechaCreacion)
                .build();
    }
}
